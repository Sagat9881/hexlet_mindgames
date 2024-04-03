package ru.apzakharov.gamecore.input_processor;


import java.io.InputStream;

public interface InputProcessor {


   Input<?> getInput();

    void awaitAndBuildInput(InputStream inputStream);

    interface Input<INPUT_TYPE> {
        INPUT_TYPE getInputBody();
    }
}
