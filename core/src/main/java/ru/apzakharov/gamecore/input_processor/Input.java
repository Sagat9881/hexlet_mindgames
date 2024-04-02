package ru.apzakharov.gamecore.input_processor;

public class Input {
    private final String inputLine;

    public Input(String inputLine) {
        if (inputLine != null && !inputLine.isBlank()) {
            this.inputLine = inputLine;
        } else
            throw new RuntimeException("Пустой ввод");

    }


    public String getInputBody() {
        return this.inputLine;
    }
}
