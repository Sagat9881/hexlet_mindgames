package ru.apzakharov.input_processor;


import lombok.extern.log4j.Log4j2;
import ru.apzakharov.data_structure.abstract_structure.Queue;
import ru.apzakharov.data_structure.structure.LinkedListQueue;
import ru.apzakharov.gamecore.input_processor.Input;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Log4j2
public class ConsoleInputProcessor implements InputProcessor {
    /* volatile - если вдруг будем работать с полем в многопоточке,
              то это ключевое слово поможет избежать грязного и фантомного чтения значения переменной  */
    private volatile Queue<Input> queue = new LinkedListQueue<>();


    public Input getInput() {
        if (queue.peek() != null) {
            return queue.poll();
        }
        return null;
    }

    @Override
    public void awaitAndBuildInput(InputStream is) {
        int inChar;
        StringBuilder s = new StringBuilder();
        try {
            //Метод блокирует поток до того момента, пока не произойдет ввод данных в консоль
            inChar = is.read();
            // Считываем всю введенную линию, т.е. пока is.available() не станет равен -1
            while (is.available() < 0) {
                s.append((char) inChar);
                inChar = is.read();
            }

            this.queue.offer(new Input(s.toString()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {

        }


    }


}
