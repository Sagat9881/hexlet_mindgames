package ru.apzakharov.context;

import lombok.Getter;
import lombok.Setter;
import ru.apzakharov.gamecore.context.GameContext;
import ru.apzakharov.gamecore.draw_processor.DrawProcessor;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.util.Set;

public interface CommandLineGameContext extends GameContext<String, String> {


    @Getter
    @Setter
    class CommandLineObjectView extends GameContext.ObjectView<String> {
        private int x1, x2;
        private int y1, y2;
        private int layer;

        public CommandLineObjectView(String colorCode, int layer, int x1, int x2, int y1, int y2) {
            super(colorCode, x1, x2, y1, y2, layer, layer);
            this.layer = layer;
        }
    }

}
