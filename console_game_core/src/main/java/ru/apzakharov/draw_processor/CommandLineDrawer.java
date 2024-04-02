package ru.apzakharov.draw_processor;

import lombok.Builder;
import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.data_structure.abstract_structure.Queue;
import ru.apzakharov.data_structure.structure.LinkedListQueue;
import ru.apzakharov.gamecore.draw_processor.DrawProcessor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.apzakharov.draw_processor.Axis.X;
import static ru.apzakharov.draw_processor.Axis.Y;

public class CommandLineDrawer implements DrawProcessor<CommandLineGameContext> {

    @Override
    public String drawFrame(CommandLineGameContext context) {
        /*
         TODO: Нужно сначала разместить в декартовой плоскости объекты по их состоянию (x1-x2,y1-y2)
               А после привести к нужному масштабу, который определяются размерами открытого консольного окна
               ...
               Но это потом, пока считаем, что масштабы совпадают
         */

        final String[][] drawMatrix = buildMatrix(context);

        fillWindowMatrix(context, drawMatrix);

        return buildFrame(drawMatrix);
    }

    private static String[][] buildMatrix(CommandLineGameContext context) {
        final Pair<Integer, Integer> gameWindowSize = context.getGameWindowSize();
        return new String[gameWindowSize.getLeft()][gameWindowSize.getRight()];
    }

    private static String buildFrame(String[][] drawMatrix) {
        return Arrays.stream(drawMatrix)
                .map(colorToken -> String.join("", colorToken))
                .collect(Collectors.joining("\n"));
    }

    private void fillWindowMatrix(CommandLineGameContext context, String[][] drawMatrix) {
        getPointQueueStream(context)
                .forEachOrdered(pointQueue -> {
                    ForkJoinPool.commonPool().invoke(new FillMatrixRecursiveTask(drawMatrix, pointQueue));
                });
    }

    private Stream<PointQueue> getPointQueueStream(CommandLineGameContext context) {
        return context.getContextObjectViews().stream()
                .map(view ->
                        PointQueue.builder()
                                .xQueue(getPoints(view, X))
                                .yQueue(getPoints(view, Y))
                                .build())
                .sorted(compareObjects());
    }

    private static Comparator<PointQueue> compareObjects() {
        return Comparator.<PointQueue>comparingInt(pointQueue -> pointQueue.yQueue.peek())
                .thenComparing(pointQueue -> pointQueue.xQueue.peek());
    }

    private Queue.ListQueue<Integer> getPoints(CommandLineGameContext.SimpleCommandLineObjectView view, Axis axis) {
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

    private static void buildQueue(int point0, int point1, LinkedListQueue<Integer> queuePoint) {
        for (int i = point0; i >= point1; i++) {
            queuePoint.offer(i);
        }
    }


    @Builder
    private static class PointQueue {
        private final String colorCode;
        private final Integer x0, y0;
        protected final Queue.ListQueue<Integer> xQueue;
        protected final Queue.ListQueue<Integer> yQueue;
    }

    private static class FillMatrixRecursiveTask extends RecursiveAction {
        private final String[][] matrix;
        private final PointQueue pointQueue;

        FillMatrixRecursiveTask(String[][] matrix, PointQueue pointQueue) {
            this.matrix = matrix;
            this.pointQueue = pointQueue;
        }

        @Override
        protected void compute() {
            //Первая строка
            Integer y0 = pointQueue.yQueue.poll();
            // Пока строки есть - заходим в цикл
            while (y0 != null) {
                //рекурсивно заполняем строку значениями
                fillLineRecursive(y0);
                //вытаскиваем следующие строки
                y0 = pointQueue.yQueue.poll();
            }

        }

        private void fillLineRecursive(Integer y0) {
            final Integer x0 = pointQueue.xQueue.poll();

            // Закончились ли точки в очереди
            boolean isXExist = x0 != null;
            boolean isYExist = y0 != null;

            // Лежит ли существующая точка в координатной плоскость
            boolean isNotOutOfWindowForY = isYExist && y0 > -1 && matrix.length > y0;
            boolean isNotOutOfWindowForX = isYExist && isXExist && x0 > -1 && matrix[y0].length > x0;

            // 1) точки которые нужно внести в матрицу существуют.
            // 2) точки лежат в пределах окна.
            if (isNotOutOfWindowForY && isNotOutOfWindowForX) {
                // TODO: Пока что оставим объекты одного цвета
                matrix[y0][x0] = pointQueue.colorCode;
                //Идем глубже в рекурсию пока не закончатся точки
                new FillMatrixRecursiveTask(matrix, pointQueue).fork().join();
            }
        }
    }

}
