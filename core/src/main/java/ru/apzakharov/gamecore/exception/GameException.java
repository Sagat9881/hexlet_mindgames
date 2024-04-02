package ru.apzakharov.gamecore.exception;

public class GameException extends RuntimeException {
    private static final String COMMON_MESSAGE = "Непредвиденная ошибка во время игры :( \n    ";
    public GameException(Exception e) {
        super(COMMON_MESSAGE + e.getMessage());
    }

    protected GameException(String message) {
        super(COMMON_MESSAGE + message);
    }
}
