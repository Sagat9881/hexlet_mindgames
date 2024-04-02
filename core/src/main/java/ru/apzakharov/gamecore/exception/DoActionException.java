package ru.apzakharov.gamecore.exception;

public class DoActionException extends GameException {
    private static final String COMMON_MESSAGE = "Ошибка обработки изменения контекста: \n    ";

    public DoActionException(Exception e) {
        super(COMMON_MESSAGE + e.getMessage());
    }
}
