package ru.apzakharov.context.entites;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.data_structure.structure.PairImpl;
import ru.apzakharov.gamecore.context.action.ActionVisitor;
import ru.apzakharov.gamecore.draw_processor.atlas_processor.AtlasProcessor;
import ru.apzakharov.input_processor.AnsiColors;

import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

@Builder
public class Rectangle2D implements ConsoleGameEntity {
    private final Deque<ActionVisitor<ConsoleGameEntity, ?>> actions = new ConcurrentLinkedDeque<>();
    private final AtlasProcessor<ConsoleGameEntity, String, ?> atlasProcessor;
    private final String colorCode;
    @Getter
    @Setter
    private Pair<Integer, Integer> x0_x1;
    @Getter
    @Setter
    private Pair<Integer, Integer> y0_y1;
    private int z;

    @Override
    public Pair<Integer, Integer> getX() {
        return x0_x1;
    }

    @Override
    public Pair<Integer, Integer> getY() {
        return y0_y1;
    }

    @Override
    public Pair<Integer, Integer> getZ() {
        return new PairImpl<>(z, z);
    }


    @Override
    public Deque<ActionVisitor<ConsoleGameEntity, ?>> getActions() {
        return actions;
    }

    @Override
    public void offerAction(ActionVisitor<ConsoleGameEntity, ?> action) {
        actions.offer(action);
    }

    @Override
    public void setActions(List<ActionVisitor<ConsoleGameEntity, ?>> offerdActions) {
        offerdActions.forEach(actions::offer);
    }

    @Override
    public String getTexture() {
        if (atlasProcessor.checkNext()) {
            atlasProcessor.next();
        }
        return colorCode + atlasProcessor.current() + AnsiColors.ANSI_RESET;
    }

    @Override
    public String getColorCode() {
        return colorCode;
    }
}
