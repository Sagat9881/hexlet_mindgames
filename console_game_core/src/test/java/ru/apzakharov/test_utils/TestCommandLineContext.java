package ru.apzakharov.test_utils;

import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.data_structure.structure.PairImpl;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class TestCommandLineContext implements CommandLineGameContext {

    private Set<SimpleCommandLineObjectView> viewMock;

    @Override
    public Set<SimpleCommandLineObjectView> getContextObjectViews() {
        return viewMock;
    }

    public TestCommandLineContext(SimpleCommandLineObjectView viewMock) {
        this.viewMock = Set.of(viewMock);
    }
    public TestCommandLineContext(SimpleCommandLineObjectView... viewMocks) {
        this.viewMock = Arrays.stream(viewMocks).collect(Collectors.toSet());
    }

    @Override
    public void offerInput(InputProcessor.Input input) {

    }

    @Override
    public Pair<Integer, Integer> getGameWindowSize() {
        return new PairImpl<>(5, 5);
    }

    @Override
    public SimpleCommandLineObjectView buildView() {
        return null;
    }
}
