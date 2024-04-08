package ru.apzakharov.gamecore.context.action;

import ru.apzakharov.gamecore.context.GameContext;
import ru.apzakharov.gamecore.context.entites.GameEntity;

/**
 * Класс - посетитель контекста, который должен совершить с объектом контекста, относительно которого был создан,
 * какое-то действие, определенное в конкретной игре.
 */
public interface ActionVisitor<GE extends GameEntity<GE>, C extends GameContext<?, ?,GE>> {
    void act(C context);
}
