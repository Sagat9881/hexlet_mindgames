package ru.apzakharov.input_processor;


import ru.apzakharov.gamecore.input_processor.InputProcessor;

public class StringInput  implements InputProcessor.Input<String>
{
    private final String inputLine;

    public StringInput(String inputBody) {
        this.inputLine = inputBody;
    }



    public String getInputBody() {
        return this.inputLine;
    }
}
