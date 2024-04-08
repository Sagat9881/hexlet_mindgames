package ru.apzakharov.gamecore.draw_processor;

import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.gamecore.context.GameContext;

import java.util.Set;

public interface DrawProcessor<FRAME_CONTAINER> {

// TODO: Цвет и текстура на уровне одной строки: комбинация ASCII кода цвета, символа и ASCII кода отключения цвета для следующих фреймов
    FRAME_CONTAINER drawFrame(Set<GameContext.ObjectView<FRAME_CONTAINER,FRAME_CONTAINER>> views, Pair<Integer, Integer> gameWindowSize);
}
