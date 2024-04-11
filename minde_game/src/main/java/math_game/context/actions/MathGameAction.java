package math_game.context.actions;

import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.context.entites.ConsoleGameEntity;
import ru.apzakharov.gamecore.context.action.ActionVisitor;

public interface MathGameAction extends ActionVisitor<ConsoleGameEntity, CommandLineGameContext> {

}

