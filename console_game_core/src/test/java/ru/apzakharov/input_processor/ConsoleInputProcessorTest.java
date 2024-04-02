package ru.apzakharov.input_processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.apzakharov.test_utils.TestInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class ConsoleInputProcessorTest {
    private ConsoleInputProcessor inputProcessor;

    @BeforeEach
    void setUp() {
        inputProcessor = new ConsoleInputProcessor();
    }

    @Test
    void getInput() {
    }

    @Test
    void awaitAndBuildInput() {

        final TestInputStream is = new TestInputStream();
        inputProcessor.awaitAndBuildInput(is);
        assertThatThrownBy(() -> inputProcessor.getInput()).isInstanceOf(IllegalStateException.class);
    }
}