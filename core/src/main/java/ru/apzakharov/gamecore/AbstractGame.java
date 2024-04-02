package ru.apzakharov.gamecore;

import ru.apzakharov.data_structure.structure.LinkedListQueue;
import ru.apzakharov.gamecore.action.ActionVisitor;
import ru.apzakharov.gamecore.context.GameContext;
import ru.apzakharov.gamecore.draw_processor.DrawProcessor;
import ru.apzakharov.gamecore.exception.DoActionException;
import ru.apzakharov.gamecore.exception.DrawFrameException;
import ru.apzakharov.gamecore.exception.GameException;
import ru.apzakharov.gamecore.exception.ProcessInputException;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Queue;

public abstract class AbstractGame<GAME_CONTEXT extends GameContext> implements Runnable {

    protected final Queue<ActionVisitor> actionVisitorQueue = new LinkedListQueue<>();
    protected final InputProcessor inputProcessor;
    protected final DrawProcessor<GAME_CONTEXT> drawProcessor;
    protected final OutputStream outputStream;
    protected final InputStream inputStream;
    protected final GAME_CONTEXT context;

    protected AbstractGame(GAME_CONTEXT context,
                           InputProcessor inputProcessor,
                           DrawProcessor<GAME_CONTEXT> drawProcessor,
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
            drawProcessor.drawFrame(context, outputStream);
        } catch (Exception e) {
            throw new DrawFrameException(e);
        }
    }

    protected void processAction() {
        try {
            actionVisitorQueue.forEach(context::visitForAction);
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
