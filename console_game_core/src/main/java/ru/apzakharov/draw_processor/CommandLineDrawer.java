package ru.apzakharov.draw_processor;

import lombok.Builder;
import lombok.Getter;
import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.data_structure.abstract_structure.Queue;
import ru.apzakharov.data_structure.structure.LinkedListQueue;
import ru.apzakharov.gamecore.draw_processor.DrawProcessor;
import ru.apzakharov.input_processor.AnsiColors;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.apzakharov.draw_processor.Axis.X;
import static ru.apzakharov.draw_processor.Axis.Y;

public class CommandLineDrawer implements DrawProcessor<CommandLineGameContext> {
    public static final String EMMIT = "*";
    public static final String EMPTY_EMMIT = AnsiColors.ANSI_WHITE.colorCode + " ~ " + AnsiColors.ANSI_RESET.colorCode;

    @Override
    public String drawFrame(CommandLineGameContext context) {
        /*
         TODO: Нужно сначала разместить в декартовой плоскости объекты по их состоянию (x1-x2,y1-y2)
               А после привести к нужному масштабу, который определяются размерами открытого консольного окна
               ...
               Но это потом, пока считаем, что масштабы совпадают
         */

        final String[][] drawMatrix = preFillMatrix(context);

        fillWindowMatrix(context, drawMatrix);

        return buildFrame(drawMatrix);
    }

    private static String[][] preFillMatrix(CommandLineGameContext context) {
        final Pair<Integer, Integer> gameWindowSize = context.getGameWindowSize();
        String[][] matrix = new String[gameWindowSize.getLeft()][gameWindowSize.getRight()];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new String[gameWindowSize.getRight()];
            Arrays.fill(matrix[i], EMPTY_EMMIT);
        }
        return matrix;
    }

    private static String buildFrame(String[][] drawMatrix) {
        return Arrays.stream(drawMatrix)
                .map(colorToken -> String.join("", colorToken))
                .collect(Collectors.joining("\n"));
    }

    private void fillWindowMatrix(CommandLineGameContext context, String[][] drawMatrix) {
        try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            final Map<Integer, List<PointsHolder>> pointsByLayers = getPointQueueStream(context)
                    .collect(Collectors.groupingBy(PointsHolder::getLayer, Collectors.toList()));

            pointsByLayers.keySet().forEach(key -> {
                pointsByLayers.get(key)
                        .forEach(pointsHolder -> {
                            forkJoinPool.invoke(new FillMatrixRecursiveTask(drawMatrix, pointsHolder));
                        });
            });


//
        } catch (Exception e) {
//         :(
        }
    }

    private Stream<PointsHolder> getPointQueueStream(CommandLineGameContext context) {
        return context.getContextObjectViews().stream()
                .map(view ->
                        PointsHolder.builder()
                                .xVector(getPoints(view, X))
                                .yVector(getPoints(view, Y))
                                .colorCode(view.getColorCode())
                                .layer(view.getLayer())
                                .build())
                .sorted(compareObjects());
    }

    private static Comparator<PointsHolder> compareObjects() {
        return Comparator.<PointsHolder>comparingInt(pointsHolder -> pointsHolder.yVector.peek())
                .thenComparing(pointsHolder -> pointsHolder.xVector.peek());
    }

    private Queue.ListQueue<Integer> getPoints(CommandLineGameContext.CommandLineObjectView view, Axis axis) {
        final LinkedListQueue<Integer> queuePoint = new LinkedListQueue<>();
        switch (axis) {
            case X:
                buildQueue(view.getX1(), view.getX2(), queuePoint);
                break;
            case Y:
                buildQueue(view.getY1(), view.getY2(), queuePoint);
        }
        return queuePoint;
    }

    private static void buildQueue(int point0, int point1, LinkedListQueue<Integer> pointsVector) {
        for (int i = point0; i <= point1; i++) {
            pointsVector.offer(i);
        }
    }


    @Builder
    @Getter
    private static final class PointsHolder {
        private final String colorCode;
        private final Integer x0;
        private final Integer y0;
        private final int layer;
        private final Queue.ListQueue<Integer> xVector;
        private final Queue.ListQueue<Integer> yVector;

        private PointsHolder(String colorCode, Integer x0, Integer y0, int layer, Queue.ListQueue<Integer> xVector,
                             Queue.ListQueue<Integer> yVector) {
            this.colorCode = colorCode;
            this.x0 = x0;
            this.y0 = y0;
            this.layer = layer;
            this.xVector = xVector;
            this.yVector = yVector;
        }

        public String colorCode() {
            return colorCode;
        }

        public Integer x0() {
            return x0;
        }

        public Integer y0() {
            return y0;
        }

        public int layer() {
            return layer;
        }

        public Queue.ListQueue<Integer> xVector() {
            return xVector;
        }

        public Queue.ListQueue<Integer> yVector() {
            return yVector;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (PointsHolder) obj;
            return Objects.equals(this.colorCode, that.colorCode) &&
                    Objects.equals(this.x0, that.x0) &&
                    Objects.equals(this.y0, that.y0) &&
                    this.layer == that.layer &&
                    Objects.equals(this.xVector, that.xVector) &&
                    Objects.equals(this.yVector, that.yVector);
        }

        @Override
        public int hashCode() {
            return Objects.hash(colorCode, x0, y0, layer, xVector, yVector);
        }

        @Override
        public String toString() {
            return "PointsHolder[" +
                    "colorCode=" + colorCode + ", " +
                    "x0=" + x0 + ", " +
                    "y0=" + y0 + ", " +
                    "layer=" + layer + ", " +
                    "xVector=" + xVector + ", " +
                    "yVector=" + yVector + ']';
        }

    }

    private static class FillMatrixRecursiveTask extends RecursiveAction {

        private final String[][] matrix;
        private final List<PointsHolder> pointsHolder;

        FillMatrixRecursiveTask(String[][] matrix, PointsHolder pointsHolder) {
            this.matrix = matrix;
            this.pointsHolder = List.of(pointsHolder);
        }

        @Override
        protected void compute() {
            pointsHolder.stream().map(ph -> {
                        return CompletableFuture.runAsync(() -> {
                            Integer y0 = ph.yVector.poll();
                            // Пока строки есть - заходим в рекурсию
                            if (y0 != null) {
                                //Спускаемся дальше по рекурсии
                                FillMatrixRecursiveTask childTask = new FillMatrixRecursiveTask(this.matrix, ph);
                                childTask.fork();
                                //итеративно заполняем строку значениями
                                fillLine(y0, ph.xVector, ph.colorCode);
                                childTask.join();
                            }
                        });
                    })
                    .forEach(CompletableFuture::join);

        }

        private void fillLine(Integer y0, Queue.ListQueue<Integer> xVector, String colorCode) {
            for (Integer point : xVector) {
                // Лежит ли существующая точка в координатной плоскость
                boolean isNotOutOfWindowForY = y0 > -1 && matrix.length > y0;
                boolean isNotOutOfWindowForX = point > -1 && matrix[y0].length > point;
                boolean isEmpty = Objects.equals(matrix[y0][point], EMPTY_EMMIT);

                // 1) точки которые нужно внести в матрицу существуют.
                // 2) точки лежат в пределах окна.
                if (isNotOutOfWindowForY && isNotOutOfWindowForX) {
                    // TODO: Пока что оставим объекты одного цвета
                    matrix[y0][point] = colorCode + EMMIT + AnsiColors.ANSI_RESET.colorCode;
                }
            }
        }
    }

}
