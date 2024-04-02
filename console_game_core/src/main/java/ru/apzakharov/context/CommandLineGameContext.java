package ru.apzakharov.context;

import lombok.Getter;
import ru.apzakharov.gamecore.context.GameContext;

import java.util.Set;

public interface CommandLineGameContext extends GameContext {


    Set<SimpleCommandLineObjectView> getContextObjectViews();

    @Override
    SimpleCommandLineObjectView buildView();

    @Getter
    class SimpleCommandLineObjectView extends GameContext.ObjectView<String> {
        private int x1, x2;
        private int y1, y2;

        public SimpleCommandLineObjectView(String colorCode) {
            super(colorCode);
        }
    }

}
