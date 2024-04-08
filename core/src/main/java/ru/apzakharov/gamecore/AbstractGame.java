package ru.apzakharov.gamecore;

import ru.apzakharov.gamecore.context.GameContext;
import ru.apzakharov.gamecore.context.entites.GameEntity;
import ru.apzakharov.gamecore.draw_processor.DrawProcessor;
import ru.apzakharov.gamecore.exception.DoActionException;
import ru.apzakharov.gamecore.exception.DrawFrameException;
import ru.apzakharov.gamecore.exception.GameException;
import ru.apzakharov.gamecore.exception.ProcessInputException;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public abstract class AbstractGame<INPUT, OUTPUT,GAME_ENTITY extends GameEntity<GAME_ENTITY>, GAME_CONTEXT extends GameContext<INPUT, OUTPUT,GAME_ENTITY>>
        implements Runnable {

    protected final Queue<GameEntity<?>> entities = new ConcurrentLinkedDeque<>();
    protected final InputProcessor<INPUT> inputProcessor;
    protected final DrawProcessor<OUTPUT> drawProcessor;
    //TODO: Для расширения стратегии вывода требуется какой-нибудь типа OutputProcessor
    protected final OutputStream outputStream;
    protected final InputStream inputStream;
    protected final GAME_CONTEXT context;

    protected AbstractGame(GAME_CONTEXT context,
                           InputProcessor<INPUT> inputProcessor,
                           DrawProcessor<OUTPUT> drawProcessor,
                           OutputStream outputStream,
                           InputStream inputStream) {

        this.inputProcessor = inputProcessor;
        this.drawProcessor = drawProcessor;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Exception e) {
            throw new GameException(e);
        }
    }

    private void doRun() {
        do {
            processInput();
            processAction();
            drawFrame();
        } while (true);
    }

    abstract protected void clearAndFlush();

    protected void drawFrame() {
        try {
            clearAndFlush();
            OUTPUT output = drawProcessor.drawFrame(context.buildViews(), context.getGameWindowSize());
            //TODO: Прям точно нужен процессор вывода
            if (output instanceof String) {
                outputStream.write(((String) output).getBytes());
            }
        } catch (Exception e) {
            throw new DrawFrameException(e);
        }
    }

    protected void processAction() {
        try {
            entities.parallelStream()
                    .map(GameEntity::getActions)
                    .forEach(queue -> queue.forEach((context::visitForAction)));
        } catch (Exception e) {
            throw new DoActionException(e);
        }
    }

    protected void processInput() {
        // не используем тут try с ресурсами,
        try {
            // Важно остановить текущий поток до момента, пока не дождемся ввода
            // join остановит текущий поток, пока дочерний не закончится.
            // Дочерний блокирует породивший его поток пока не получит ввод -> родительский поток продолжится только когда, будет получен ввод
            new Thread(() -> inputProcessor.awaitAndBuildInput(inputStream)).join();

            context.offerInput(inputProcessor.getInput());
        } catch (Exception e) {
            throw new ProcessInputException(e);
        }
    }


}
