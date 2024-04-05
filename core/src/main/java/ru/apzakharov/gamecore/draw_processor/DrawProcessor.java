package ru.apzakharov.gamecore.draw_processor;

import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.gamecore.context.GameContext;
import ru.apzakharov.gamecore.context.GameObject;

import java.io.*;
import java.util.Set;

public interface DrawProcessor<FRAME_CONTAINER> {


    FRAME_CONTAINER drawFrame(Set<GameContext.ObjectView<FRAME_CONTAINER>> views, Pair<Integer, Integer> gameWindowSize);
}
