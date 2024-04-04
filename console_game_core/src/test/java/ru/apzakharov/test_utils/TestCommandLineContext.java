package ru.apzakharov.test_utils;

import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.data_structure.structure.PairImpl;
import ru.apzakharov.gamecore.context.GameObject;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TestCommandLineContext implements CommandLineGameContext {

    private Set<CommandLineObjectView> viewMock;
    private Set<GameObject> gameObjectSet = new HashSet<>();

    @Override
    public Set<CommandLineObjectView> getContextObjectViews() {
        return viewMock;
    }

    public TestCommandLineContext(CommandLineObjectView viewMock) {
        this.viewMock = Set.of(viewMock);
    }
    public TestCommandLineContext(CommandLineObjectView... viewMocks) {
        this.viewMock = Arrays.stream(viewMocks).collect(Collectors.toSet());
    }

    @Override
    public void offerInput(InputProcessor.Input input) {

    }

    @Override
    public void addContextObject(GameObject object) {
        gameObjectSet.add(object);
    }

    @Override
    public Pair<Integer, Integer> getGameWindowSize() {
        return new PairImpl<>(5, 5);
    }

    @Override
    public CommandLineObjectView buildView() {
        return null;
    }
}
