package ru.apzakharov.context.entites;

import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.gamecore.context.entites.GameEntity;

public interface ConsoleGameEntity extends GameEntity<ConsoleGameEntity> {
   //  границы отрезка - проекции объекта на ось Х
    Pair<Integer, Integer> getX();
    // границы отрезка - проекции объекта на ось У
    Pair<Integer, Integer> getY();
    // границы отрезка - проекции объекта на ось Z
    Pair<Integer, Integer> getZ();

    String getTexture();

    String getColorCode();


}
