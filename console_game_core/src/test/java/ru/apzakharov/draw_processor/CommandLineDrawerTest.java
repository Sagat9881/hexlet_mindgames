package ru.apzakharov.draw_processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.apzakharov.context.ConsoleGameContext;
import ru.apzakharov.context.actions.rectangle.RectangleMoveAction;
import ru.apzakharov.context.entites.ConsoleGameEntity;
import ru.apzakharov.context.entites.Rectangle2D;
import ru.apzakharov.context.enums.Direction;
import ru.apzakharov.data_structure.structure.PairImpl;
import ru.apzakharov.draw_processor.atlas_processor.SimpleCommandLineAtlasProcessor;
import ru.apzakharov.gamecore.context.GameContext;
import ru.apzakharov.input_processor.AnsiColors;
import ru.apzakharov.test_utils.TestConsoleContext;

import java.util.Set;

class CommandLineDrawerTest {
    public static final String TEST_EMMIT = " * ";
    private CommandLineDrawer drawer;
    private ConsoleGameContext context;

    @BeforeEach
    void setUp() {
        this.drawer = new CommandLineDrawer();

    }

    private static ConsoleGameContext.CommandLineObjectView
    getSimpleCommandLineObjectView(int x1, int x2, int y1, int y2, int layer, AnsiColors color) {
        ConsoleGameContext.CommandLineObjectView view =
                new ConsoleGameContext.CommandLineObjectView(color.colorCode, TEST_EMMIT, layer, x1, x2, y1, y2);

        return view;
    }

    @Test
    void drawView() {
        ConsoleGameContext.CommandLineObjectView blue =
                getSimpleCommandLineObjectView(0, 24, 0, 9, 1, AnsiColors.ANSI_BLUE);
        ConsoleGameContext.CommandLineObjectView green =
                getSimpleCommandLineObjectView(5, 7, 0, 9, 1, AnsiColors.ANSI_GREEN);

        ConsoleGameContext.CommandLineObjectView red =
                getSimpleCommandLineObjectView(0, 5, 3, 9, 2, AnsiColors.ANSI_RED);
        ConsoleGameContext.CommandLineObjectView cyan =
                getSimpleCommandLineObjectView(7, 9, 3, 9, 2, AnsiColors.ANSI_WHITE);

        ConsoleGameContext.CommandLineObjectView yellow =
                getSimpleCommandLineObjectView(0, 6, 6, 9, 3, AnsiColors.ANSI_YELLOW);
        ConsoleGameContext.CommandLineObjectView purple =
                getSimpleCommandLineObjectView(9, 24, 0, 9, 3, AnsiColors.ANSI_PURPLE);

        this.context = new TestConsoleContext(red, yellow, blue, green, cyan, purple);
        String drawFrame = drawer.drawFrame(Set.of(red, yellow, blue, green, cyan, purple), context.getGameWindowSize());
        System.out.println(drawFrame);
    }

    @Test
    void drawFrame3D() {
        GameContext.ObjectView<String, String> view = new GameContext.ObjectView<>(AnsiColors.ANSI_BLUE.colorCode, TEST_EMMIT, 3, 12, 6, 9, 0, 6);
        context = new TestConsoleContext(view);
        String drawFrame = drawer.drawFrame(Set.of(view), context.getGameWindowSize());
        System.out.println(drawFrame);
    }

    @Test
    void drawGameEntity() {
        context = new TestConsoleContext();
        Rectangle2D rect = Rectangle2D.builder()
                .atlasProcessor(null)
                .x0_x1(new PairImpl<>(1, 10))
                .y0_y1(new PairImpl<>(9, 6))
                .colorCode(AnsiColors.ANSI_BLUE.colorCode)
                .z(0).build();

        context.addGameInstance(rect);
        String drawFrame = drawer.drawFrame(context.buildViews(), context.getGameWindowSize());
        System.out.println(drawFrame);
    }


    @Test
    void drawManyGameEntity() throws InterruptedException {
        context = new TestConsoleContext();
        Rectangle2D rectBlue = Rectangle2D.builder()
                .atlasProcessor(new SimpleCommandLineAtlasProcessor<>())
                .x0_x1(new PairImpl<>(1, 4))
                .y0_y1(new PairImpl<>(9, 6))
                .colorCode(AnsiColors.ANSI_BLUE.colorCode)
                .z(1).build();
        Rectangle2D rectRed = Rectangle2D.builder()
                .atlasProcessor(new SimpleCommandLineAtlasProcessor<>())
                .x0_x1(new PairImpl<>(5, 8))
                .y0_y1(new PairImpl<>(9, 6))
                .colorCode(AnsiColors.ANSI_RED.colorCode)
                .z(0).build();

        Rectangle2D rectYellow = Rectangle2D.builder()
                .atlasProcessor(new SimpleCommandLineAtlasProcessor<>())
                .x0_x1(new PairImpl<>(22, 24))
                .y0_y1(new PairImpl<>(2, 0))
                .colorCode(AnsiColors.ANSI_YELLOW.colorCode)
                .z(2).build();

        Rectangle2D rectPurple = Rectangle2D.builder()
                .atlasProcessor(new SimpleCommandLineAtlasProcessor<>())
                .x0_x1(new PairImpl<>(1, 4))
                .y0_y1(new PairImpl<>(9, 6))
                .colorCode(AnsiColors.ANSI_PURPLE.colorCode)
                .z(0).build();

        context.addGameInstance(rectBlue);
        context.addGameInstance(rectRed);
        context.addGameInstance(rectYellow);
        context.addGameInstance(rectPurple);
        String drawFrame = drawer.drawFrame(context.buildViews(), context.getGameWindowSize());
        System.out.println(drawFrame);
        int i = 0;
        while (i < context.getGameWindowSize().getRight()) {
            RectangleMoveAction actionYellow = new RectangleMoveAction(rectYellow, Direction.LEFT);
            rectYellow.offerAction(actionYellow);
            actionYellow.act(context);

            RectangleMoveAction actionBlue = new RectangleMoveAction(rectBlue, Direction.RIGHT);
            rectBlue.offerAction(actionBlue);
            actionBlue.act(context);

            drawFrame = drawer.drawFrame(context.buildViews(), context.getGameWindowSize());
//            System.out.println(drawFrame+"\n".repeat(6));
            System.out.println(drawFrame);
            System.out.println("\033[H\033[2J");
            i++;
            Thread.sleep(70);

        }

    }


}