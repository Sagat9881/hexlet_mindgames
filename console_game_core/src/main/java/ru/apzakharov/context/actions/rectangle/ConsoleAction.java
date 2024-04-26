package ru.apzakharov.context.actions.rectangle;

import ru.apzakharov.context.ConsoleGameContext;
import ru.apzakharov.context.entites.ConsoleGameEntity;
import ru.apzakharov.gamecore.context.action.ActionVisitor;

public interface ConsoleAction extends ActionVisitor<ConsoleGameEntity, ConsoleGameContext> {
}
