package ru.apzakharov.context.entites;

import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.gamecore.context.entites.GameEntity;

public interface ConsoleGameEntity extends GameEntity<ConsoleGameEntity> {
    Pair<Integer, Integer> getX();

    Pair<Integer, Integer> getY();

    Pair<Integer, Integer> getZ();

    String getTexture();

    String getColorCode();


}
