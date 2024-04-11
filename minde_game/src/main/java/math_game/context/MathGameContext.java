package math_game.context;

import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.context.entites.ConsoleGameEntity;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.data_structure.structure.PairImpl;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class MathGameContext implements CommandLineGameContext {
    private final Deque<InputProcessor.Input<String>> inputDeque = new LinkedList<>();
    private final Set<ConsoleGameEntity> entities = new HashSet<>();
    private final Pair<Integer, Integer> gameSize = new PairImpl<>(30, 20);


    public void offerInput(InputProcessor.Input<String> input) {
        inputDeque.offer(input);
    }

    public void addGameInstance(ConsoleGameEntity gameInstance) {
        entities.add(gameInstance);
    }

    public boolean isInstanceAdded(ConsoleGameEntity gameInstance) {
        return entities.contains(gameInstance);
    }

    public Pair<Integer, Integer> getGameWindowSize() {
        return gameSize;
    }

    public Set<ObjectView<String, String>> buildViews() {
        return entities.stream().map(this::buildView).collect(Collectors.toSet());
    }
}
