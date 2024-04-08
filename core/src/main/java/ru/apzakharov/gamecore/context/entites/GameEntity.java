package ru.apzakharov.gamecore.context.entites;

import ru.apzakharov.gamecore.context.action.ActionVisitor;

import java.util.Deque;
import java.util.List;

public interface GameEntity<GE extends GameEntity<GE>> {
    Deque<ActionVisitor<GE,?>> getActions();

    void offerAction(ActionVisitor<GE,?> action);

    void setActions(List<ActionVisitor<GE,?>> action);

   Object getTexture();

}
