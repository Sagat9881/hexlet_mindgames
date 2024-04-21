package ru.apzakharov.context.actions.rectangle;

import ru.apzakharov.context.ConsoleGameContext;
import ru.apzakharov.context.entites.Rectangle2D;
import ru.apzakharov.context.enums.Direction;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.data_structure.structure.PairImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RectangleMoveAction implements RectangleAction {
    private final Rectangle2D entity;
    private final List<Direction> directions;
    private int frameBySecond; // speed


    public RectangleMoveAction(Rectangle2D entity, Direction direction) {
        this.entity = entity;
        this.directions = List.of(direction);
        entity.offerAction(this);
    }

    public RectangleMoveAction(Rectangle2D entity, Direction... direction) {
        this.entity = entity;
        this.directions = Arrays.stream(direction).collect(Collectors.toList());
        entity.offerAction(this);
    }


    @Override
    public void act(ConsoleGameContext context) {
        directions.forEach(direction -> {
            switch (direction) {
                case UP: {
                    Pair<Integer, Integer> x = entity.getY();
                    Integer y0 = x.getRight();
                    Integer y1 = x.getLeft();
                    entity.setY0_y1(new PairImpl<>((y0 - 1), (y1 - 1)));
                    break;
                }
                case DOWN: {
                    Pair<Integer, Integer> x = entity.getY();
                    Integer y0 = x.getRight();
                    Integer y1 = x.getLeft();
                    entity.setY0_y1(new PairImpl<>((y0 + 1), (y1 + 1)));
                    break;
                }
                case LEFT: {
                    Pair<Integer, Integer> x = entity.getX();
                    Integer x0 = x.getRight();
                    Integer x1 = x.getLeft();
                    entity.setX0_x1(new PairImpl<>((x0 - 1), (x1 - 1)));
                    break;
                }
                case RIGHT: {
                    Pair<Integer, Integer> x = entity.getX();
                    Integer x0 = x.getRight();
                    Integer x1 = x.getLeft();
                    entity.setX0_x1(new PairImpl<>((x0 + 1), (x1 + 1)));
                    break;
                }
            }
        });
    }

}
