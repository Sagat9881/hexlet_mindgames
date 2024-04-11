package math_game;

import lombok.Getter;
import math_game.context.MathGameContext;
import math_game.entites.MathGameEntity;
import ru.apzakharov.AbstractConsoleGame;
import ru.apzakharov.draw_processor.CommandLineDrawer;
import ru.apzakharov.input_processor.ConsoleBlockingInputProcessor;

import java.util.Set;

public class MathGame extends AbstractConsoleGame<MathGameContext> {
    @Getter
    private final Set<MathGameEntity> entities;

    public MathGame(Set<MathGameEntity> entities) {
        super(new MathGameContext(), new ConsoleBlockingInputProcessor(), new CommandLineDrawer());
        this.entities = entities;
    }

    @Override
    public void run() {
        entities.forEach(context::addGameInstance);
        super.run();
    }

    @Override
    protected void processAction() {
        entities.forEach(context::visitForAction);
    }
}
