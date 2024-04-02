package ru.apzakharov.gamecore.draw_processor;

import ru.apzakharov.gamecore.context.GameContext;

import java.io.IOException;
import java.io.OutputStream;

public interface DrawProcessor<C extends GameContext> {
    default void drawFrame(C context, OutputStream outputStream) {
        try {
            outputStream.write(drawFrame(context).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String drawFrame(C context);
}
