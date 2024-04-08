package ru.apzakharov.context.actions.rectangle;

import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.context.entites.ConsoleGameEntity;
import ru.apzakharov.gamecore.context.action.ActionVisitor;

public interface RectangleAction extends ActionVisitor<ConsoleGameEntity,CommandLineGameContext> {
}
