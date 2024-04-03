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
     void setUp(){
         this.drawer = new CommandLineDrawer();

         CommandLineGameContext.SimpleCommandLineObjectView mockObject =
                 new CommandLineGameContext.SimpleCommandLineObjectView(AnsiColors.ANSI_RED.colorCode);

         mockObject.setX1(0);
         mockObject.setX2(2);

         mockObject.setY1(0);
         mockObject.setY2(1);

         CommandLineGameContext.SimpleCommandLineObjectView mockObject2 =
                 new CommandLineGameContext.SimpleCommandLineObjectView(AnsiColors.ANSI_GREEN.colorCode);

         mockObject2.setX1(1);
         mockObject2.setX2(4);

         mockObject2.setY1(2);
         mockObject2.setY2(4);

         this.context = new TestCommandLineContext(mockObject,mockObject2);
     }

    @Test
    void drawFrame() {
        String drawFrame = drawer.drawFrame(context);
        System.out.println(drawFrame);

    }
}