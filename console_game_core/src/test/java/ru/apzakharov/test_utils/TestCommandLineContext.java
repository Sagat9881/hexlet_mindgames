package ru.apzakharov.test_utils;

import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.data_structure.structure.PairImpl;
import ru.apzakharov.draw_processor.CommandLineDrawer;
import ru.apzakharov.gamecore.context.GameContext;
import ru.apzakharov.gamecore.context.GameObject;
import ru.apzakharov.gamecore.input_processor.InputProcessor;
import ru.apzakharov.input_processor.ConsoleBlockingInputProcessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TestCommandLineContext implements CommandLineGameContext {

    private Set<GameContext.ObjectView<String>> viewMock;
    private Set<GameObject> gameObjectSet = new HashSet<>();



    public TestCommandLineContext(ObjectView<String> viewMock) {
        this.viewMock = Set.of(viewMock);
    }
    public TestCommandLineContext(CommandLineObjectView... viewMocks) {
        this.viewMock = Arrays.stream(viewMocks).collect(Collectors.toSet());
    }

    @Override
    public void offerInput(InputProcessor.Input<String> input) {

    }

    @Override
    public void addContextObject(GameObject object) {
        gameObjectSet.add(object);
    }

    @Override
    public Pair<Integer, Integer> getGameWindowSize() {
        return new PairImpl<>(25, 10);
    }

    @Override
    public Set<ObjectView<String>> buildViews() {
        return viewMock;
    }

}
