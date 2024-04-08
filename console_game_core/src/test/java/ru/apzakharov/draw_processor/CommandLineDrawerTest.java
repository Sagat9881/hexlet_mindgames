package ru.apzakharov.draw_processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.gamecore.context.GameContext;
import ru.apzakharov.input_processor.AnsiColors;
import ru.apzakharov.test_utils.TestCommandLineContext;

class CommandLineDrawerTest {
    public static final String TEST_EMMIT = " * ";
    private CommandLineDrawer drawer;
    private CommandLineGameContext context;

    @BeforeEach
    void setUp() {
        this.drawer = new CommandLineDrawer();

    }

    private static CommandLineGameContext.CommandLineObjectView
    getSimpleCommandLineObjectView(int x1, int x2, int y1, int y2, int layer, AnsiColors color) {
        CommandLineGameContext.CommandLineObjectView view =
                new CommandLineGameContext.CommandLineObjectView(color.colorCode, TEST_EMMIT, layer, x1, x2, y1, y2);

        return view;
    }

    @Test
    void drawFrame() {
        CommandLineGameContext.CommandLineObjectView blue =
                getSimpleCommandLineObjectView(0, 24, 0, 9, 1, AnsiColors.ANSI_BLUE);
        CommandLineGameContext.CommandLineObjectView green =
                getSimpleCommandLineObjectView(5, 7, 0, 9, 1, AnsiColors.ANSI_GREEN);

        CommandLineGameContext.CommandLineObjectView red =
                getSimpleCommandLineObjectView(0, 5, 3, 9, 2, AnsiColors.ANSI_RED);
        CommandLineGameContext.CommandLineObjectView cyan =
                getSimpleCommandLineObjectView(7, 9, 3, 9, 2, AnsiColors.ANSI_WHITE);

        CommandLineGameContext.CommandLineObjectView yellow =
                getSimpleCommandLineObjectView(0, 6, 6, 9, 3, AnsiColors.ANSI_YELLOW);
        CommandLineGameContext.CommandLineObjectView purple =
                getSimpleCommandLineObjectView(9, 24, 0, 9, 3, AnsiColors.ANSI_PURPLE);

        this.context = new TestCommandLineContext(red, yellow, blue, green, cyan, purple);
        String drawFrame = drawer.drawFrame(context.buildViews(), context.getGameWindowSize());
        System.out.println(drawFrame);
    }

    @Test
    void drawFrame3D() {
        GameContext.ObjectView<String, String> view = new GameContext.ObjectView<>(AnsiColors.ANSI_BLUE.colorCode, TEST_EMMIT, 3, 12, 6, 9, 0, 6);
        context = new TestCommandLineContext(view);
        String drawFrame = drawer.drawFrame(context.buildViews(), context.getGameWindowSize());
        System.out.println(drawFrame);
    }

}