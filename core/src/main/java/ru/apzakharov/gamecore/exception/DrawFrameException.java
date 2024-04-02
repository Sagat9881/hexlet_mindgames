package ru.apzakharov.gamecore.exception;

public class DrawFrameException extends GameException {
    private static final String COMMON_MESSAGE = "Ошибка отрисовки фрэйма: \n    ";
    public DrawFrameException(Exception e) {
        super(COMMON_MESSAGE + e.getMessage());
    }
}
