package ru.apzakharov;

import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.context.entites.ConsoleGameEntity;
import ru.apzakharov.gamecore.AbstractGame;
import ru.apzakharov.gamecore.draw_processor.DrawProcessor;
import ru.apzakharov.input_processor.ConsoleBlockingInputProcessor;

import java.io.IOException;

public abstract class AbstractConsoleGame<GAME_CONTEXT extends CommandLineGameContext> extends AbstractGame<String, String, ConsoleGameEntity, CommandLineGameContext> {


    protected AbstractConsoleGame(GAME_CONTEXT context, ConsoleBlockingInputProcessor inputProcessor, DrawProcessor<String> drawProcessor) {
        super(context, inputProcessor, drawProcessor, System.out, System.in);
    }

    @Override
    protected void clearAndFlush() {
        try {
            // TODO: Пока что попробуем ANSI коды для очистки
            //       Это может работать не везде, имеет смысл проверять в какой среде происходит рантайм
            //       и в зависимости от результатов вызывать ту или иную команду ОС для работы с консолью
            outputStream.flush();
            outputStream.write("\033[H\033[2J".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
