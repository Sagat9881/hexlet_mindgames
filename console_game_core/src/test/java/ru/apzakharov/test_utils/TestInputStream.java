package ru.apzakharov.test_utils;

import java.io.IOException;
import java.io.InputStream;

public class TestInputStream extends InputStream {
    private String input;

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public int read() throws IOException {
        if (input != null && !input.isBlank()) {
            this.input = removeFirst(input);
            return input.length();
        } else {
            return -1;
        }
    }

    private String removeFirst(String line){
        if(line.isBlank()) return line;

        return line.replaceFirst(line.split("")[0],"");
    }
}
