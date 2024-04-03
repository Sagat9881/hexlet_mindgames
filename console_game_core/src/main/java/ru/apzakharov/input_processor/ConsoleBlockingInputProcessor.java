package ru.apzakharov.input_processor;


import lombok.extern.log4j.Log4j2;
import ru.apzakharov.data_structure.abstract_structure.Queue;
import ru.apzakharov.data_structure.structure.LinkedListQueue;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
//TODO: Нужен еще один класс, который не будет блокировать поток во время чтения
public class ConsoleBlockingInputProcessor implements InputProcessor {
    /* volatile - если вдруг будем работать с полем в многопоточке,
              то это ключевое слово поможет избежать грязного и фантомного чтения значения переменной
              Впрочем, можно и просто подготовить саму структуру для работы в многопоточке
               */
    private final Queue<StringInput> queue = new LinkedListQueue<>();


    public Input<String> getInput() {
        return queue.poll();
    }

    @Override
    public void awaitAndBuildInput(InputStream is) {
        int inChar;
        StringBuilder stringAccum = new StringBuilder();
        try {
            //Метод блокирует поток до того момента, пока не произойдет ввод данных в консоль

            // Считываем всю введенную линию, т.е. пока is.available() не станет равен 0
            do {
                inChar = is.read();
                stringAccum.append((char) inChar);
            } while (is.available() > -1);

            final String inputBody = stringAccum.toString();
            if (!inputBody.isBlank()) {
                this.queue.offer(new StringInput(inputBody));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
