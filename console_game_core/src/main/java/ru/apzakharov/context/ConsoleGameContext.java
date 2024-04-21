package ru.apzakharov.context;

import lombok.Getter;
import lombok.Setter;
import ru.apzakharov.context.entites.ConsoleGameEntity;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.gamecore.context.GameContext;

public interface ConsoleGameContext extends GameContext<String, String, ConsoleGameEntity> {

    @Override
    default CommandLineObjectView buildView(ConsoleGameEntity entity) {
        Pair<Integer, Integer> x = entity.getX();
        Pair<Integer, Integer> y = entity.getY();
        Pair<Integer, Integer> z = entity.getZ();
        String texture = entity.getTexture();
        String colorCode = entity.getColorCode();

        return new CommandLineObjectView(
                colorCode,
                texture,
                z.getLeft(),
                x.getRight(),
                x.getLeft(),
                y.getLeft(),
                y.getRight()
        );
    }


    @Getter
    @Setter
    class CommandLineObjectView extends GameContext.ObjectView<String, String> {
        private int layer;

        public CommandLineObjectView(String colorCode, String texture, int layer, int x1, int x2, int y1, int y2) {
            super(colorCode, texture, x1, x2, y1, y2, layer, layer);
            this.layer = layer;
        }
    }

}
