package ru.apzakharov.gamecore.action;

import ru.apzakharov.gamecore.context.GameContext;

/**
 * Класс - посетитель контекста, который должен совершить с ним какое-то действие, определенное в конкретной игре.
 */
public interface ActionVisitor {
    GameContext act(GameContext context);
    boolean isActionExist(GameContext context);
}
