package ru.apzakharov.test_utils;

import ru.apzakharov.context.ConsoleGameContext;
import ru.apzakharov.context.entites.ConsoleGameEntity;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.data_structure.structure.PairImpl;
import ru.apzakharov.gamecore.context.GameContext;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TestConsoleContext implements ConsoleGameContext {

    private Set<GameContext.ObjectView<String, String>> viewMock;
    private Set<ConsoleGameEntity> gameEntitySet = new HashSet<>();

    public TestConsoleContext(ObjectView<String, String> viewMock) {
        this.viewMock = Set.of(viewMock);
    }

    public TestConsoleContext(CommandLineObjectView... viewMocks) {
        this.viewMock = Arrays.stream(viewMocks).collect(Collectors.toSet());
    }

    @Override
    public void offerInput(InputProcessor.Input<String> input) {    }

    @Override
    public void addGameInstance(ConsoleGameEntity gameInstance) {
        gameEntitySet.add(gameInstance);
    }

    @Override
    public boolean isInstanceAdded(ConsoleGameEntity gameInstance) {
        return true;
    }


    @Override
    public Pair<Integer, Integer> getGameWindowSize() {
        return new PairImpl<>(40, 10);
    }

    @Override
    public Set<ObjectView<String, String>> buildViews() {
        return gameEntitySet.stream().map(this::buildView).collect(Collectors.toSet());
    }



}
