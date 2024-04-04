package ru.apzakharov.draw_processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.input_processor.AnsiColors;
import ru.apzakharov.test_utils.TestCommandLineContext;

class CommandLineDrawerTest {
    private CommandLineDrawer drawer;
    private CommandLineGameContext context;

    @BeforeEach
    void setUp() {
        this.drawer = new CommandLineDrawer();

        CommandLineGameContext.CommandLineObjectView mockObject2 = getSimpleCommandLineObjectView(0, 2, 0, 3, 0, AnsiColors.ANSI_BLUE);
        CommandLineGameContext.CommandLineObjectView mockObject = getSimpleCommandLineObjectView(0, 2, 3, 4, 1, AnsiColors.ANSI_RED);

        this.context = new TestCommandLineContext(mockObject, mockObject2);
    }

    private static CommandLineGameContext.CommandLineObjectView
    getSimpleCommandLineObjectView(int x1, int x2, int y1, int y2, int layer, AnsiColors color) {
        CommandLineGameContext.CommandLineObjectView view =
                new CommandLineGameContext.CommandLineObjectView(color.colorCode, layer);

        view.setX1(x1);
        view.setX2(x2);
        view.setY1(y1);
        view.setY2(y2);

        return view;
    }

    @Test
    void drawFrame() {
        String drawFrame = drawer.drawFrame(context);
        System.out.println(drawFrame);
    }

}