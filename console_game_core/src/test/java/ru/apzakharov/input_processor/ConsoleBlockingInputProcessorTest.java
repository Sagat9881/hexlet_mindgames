package ru.apzakharov.input_processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.apzakharov.test_utils.TestInputStream;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConsoleBlockingInputProcessorTest {
    private static final String TEST = "test";
    private ConsoleBlockingInputProcessor inputProcessor;

    @BeforeEach
    void setUp() {
        inputProcessor = new ConsoleBlockingInputProcessor();
    }


    @Test
    void awaitAndBuildInput() {
        TestInputStream testInputStream = new TestInputStream();

        inputProcessor.awaitAndBuildInput(testInputStream);
        assertNull(inputProcessor.getInput());

        testInputStream.setInput(TEST);
        inputProcessor.awaitAndBuildInput(testInputStream);
        assertEquals(TEST,inputProcessor.getInput().getInputBody());
    }
}