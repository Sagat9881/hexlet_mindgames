package ru.apzakharov.test_utils;

import java.io.IOException;
import java.io.InputStream;

public class TestInputStream extends InputStream {
    private String input;

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public int available() throws IOException {
        if (input != null && !input.isBlank()) {
            return input.length();
        }

        return -1;
    }

    @Override
    public int read() throws IOException {
        if (input != null && !input.isBlank()) {
            char charValue = input.toCharArray()[0];
            this.input = removeFirst(input);
            return charValue;
        } else {
            return -1;
        }
    }

    private String removeFirst(String line) {
        if (line.isBlank()) return line;

        return line.replaceFirst(line.split("")[0], "");
    }
}
