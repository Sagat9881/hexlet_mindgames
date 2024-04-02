package ru.apzakharov.gamecore.exception;

public class ProcessInputException extends GameException {
    private static final String COMMON_MESSAGE = "Не удалось обработать пользовательский ввод: \n    ";

    public ProcessInputException(Exception e) {
        super(COMMON_MESSAGE + e.getMessage());
    }
}
