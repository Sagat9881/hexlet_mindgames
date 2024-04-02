package ru.apzakharov.gamecore.input_processor;

import java.io.InputStream;
import java.util.Optional;

public interface InputProcessor {


    Optional<Input> getInput();

    void awaitAndBuildInput(InputStream inputStream);
}
