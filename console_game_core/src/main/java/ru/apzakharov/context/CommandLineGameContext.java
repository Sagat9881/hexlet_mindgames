package ru.apzakharov.context;

import lombok.Getter;
import lombok.Setter;
import ru.apzakharov.gamecore.context.GameContext;

import java.util.Set;

public interface CommandLineGameContext extends GameContext {


    Set<CommandLineObjectView> getContextObjectViews();


    @Override
    CommandLineObjectView buildView();

    @Getter
    @Setter
    class CommandLineObjectView extends GameContext.ObjectView<String> {
        private int x1, x2;
        private int y1, y2;

        public CommandLineObjectView(String colorCode, int layer) {
            super(colorCode, layer);
        }
    }

}
