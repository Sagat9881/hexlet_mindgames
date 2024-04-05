package ru.apzakharov.draw_processor;

import lombok.Builder;
import lombok.Getter;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.data_structure.structure.PairImpl;
import ru.apzakharov.gamecore.context.GameContext;
import ru.apzakharov.gamecore.draw_processor.DrawProcessor;
import ru.apzakharov.input_processor.AnsiColors;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.apzakharov.draw_processor.Axis.*;

public class CommandLineDrawer implements DrawProcessor<String> {
    public static final String EMMIT = " * ";
    public static final String EMPTY_EMMIT = AnsiColors.ANSI_WHITE.colorCode + " ~ " + AnsiColors.ANSI_RESET.colorCode;

    @Override
    public String drawFrame(Set<GameContext.ObjectView<String>> contextObjectViews, Pair<Integer, Integer> gameWindowSize) {
        /*
         TODO: Нужно сначала разместить в декартовой плоскости объекты по их состоянию (x1-x2,y1-y2)
               А после привести к нужному масштабу, который определяются размерами открытого консольного окна
               ...
               Но это потом, пока считаем, что масштабы совпадают
         */

        final String[][] drawMatrix = preFillMatrix(gameWindowSize);

        fillWindowMatrix(contextObjectViews, drawMatrix);

        return buildFrame(drawMatrix);
    }

    private static String[][] preFillMatrix(Pair<Integer, Integer> gameWindowSize) {
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

    private void fillWindowMatrix(Set<GameContext.ObjectView<String>> contextObjectViews, String[][] drawMatrix) {
        try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            final Map<Pair<Integer, Integer>, List<PointsHolder>> pointsByLayers = getPointQueueStream(contextObjectViews)
                    .collect(Collectors.groupingBy(ph -> vectorBorders(ph.zVector), Collectors.toList()));

            pointsByLayers.keySet().stream()
                    .sorted(Comparator.comparingInt(Pair::getLeft))
                    .forEachOrdered(key -> {
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

    private Stream<PointsHolder> getPointQueueStream(Set<GameContext.ObjectView<String>> contextObjectViews) {
        return contextObjectViews.stream()
                .map(view ->
                        PointsHolder.builder()
                                .xVector(getPoints(view, X))
                                .yVector(getPoints(view, Y))
                                .zVector(getPoints(view, Z))
                                .colorCode(view.getColorCode())
                                .build())
                .sorted(compareObjects());
    }

    private static Comparator<PointsHolder> compareObjects() {
        return Comparator.<PointsHolder>comparingInt(pointsHolder -> pointsHolder.yVector.peek())
                .thenComparing(pointsHolder -> pointsHolder.xVector.peek())
                .thenComparing(pointsHolder -> pointsHolder.zVector.peek());
    }

    private java.util.Deque<Integer> getPoints(GameContext.ObjectView<String> view, Axis axis) {
        final java.util.Deque<Integer> queuePoint = new ConcurrentLinkedDeque<>();
        ;
        switch (axis) {
            case X:
                buildQueue(view.getX1(), view.getX2(), queuePoint);
                break;
            case Y:
                buildQueue(view.getY1(), view.getY2(), queuePoint);
                break;
            case Z:
                buildQueue(view.getZ1(), view.getZ2(), queuePoint);
                break;
        }
        return queuePoint;
    }

    private static void buildQueue(int point0, int point1, java.util.Deque<Integer> pointsVector) {
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
        private java.util.Deque<Integer> xVector = new ConcurrentLinkedDeque<>();
        private java.util.Deque<Integer> yVector = new ConcurrentLinkedDeque<>();
        private java.util.Deque<Integer> zVector = new ConcurrentLinkedDeque<>();
        ;

        private PointsHolder(String colorCode, Integer x0, Integer y0, java.util.Deque<Integer> xVector,
                             java.util.Deque<Integer> yVector, java.util.Deque<Integer> zVector) {
            this.colorCode = colorCode;
            this.x0 = x0;
            this.y0 = y0;
            this.zVector = zVector;
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

        public java.util.Deque<Integer> xVector() {
            return xVector;
        }

        public java.util.Deque<Integer> yVector() {
            return yVector;
        }

        public java.util.Deque<Integer> zVector() {
            return zVector;
        }


    }

    private static class FillMatrixRecursiveTask extends RecursiveAction {

        private final String[][] matrix;
        private final List<PointsHolder> pointsHolders;

        FillMatrixRecursiveTask(String[][] matrix, PointsHolder pointsHolders) {
            this.matrix = matrix;
            this.pointsHolders = List.of(pointsHolders);
        }

        @Override
        protected void compute() {
            pointsHolders.stream().map(ph -> {
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
                    .forEach(cf -> {
                        try {
                            cf.join();
                            cf.get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        private void fillLine(Integer y0, java.util.Deque<Integer> xVector, String colorCode) {
            for (Integer point : xVector) {
                // Лежит ли существующая точка в видимой плоскости
                boolean isNotOutOfWindowForY = y0 > -1 && matrix.length > y0;
                boolean isNotOutOfWindowForX = point > -1 && matrix[y0].length > point;

                // 1) точки которые нужно внести в матрицу существуют.
                // 2) точки лежат в пределах окна.
                if (isNotOutOfWindowForY && isNotOutOfWindowForX) {
                    // TODO: Пока что оставим объекты одного цвета
                    matrix[y0][point] = colorCode + EMMIT + AnsiColors.ANSI_RESET.colorCode;
                }
            }
        }
    }

    static Pair<Integer, Integer> vectorBorders(Deque<Integer> vector) {
        return vector.peek() != null ?
                new PairImpl<>(vector.peekFirst(), vector.peekLast())
                : new PairImpl<>(0, 0);
    }

}
