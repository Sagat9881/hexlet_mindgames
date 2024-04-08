package ru.apzakharov.gamecore.draw_processor.atlas_processor;

import ru.apzakharov.gamecore.context.entites.GameEntity;

public interface AtlasProcessor<GAME_ENTITY extends GameEntity<GAME_ENTITY>, FRAME_TYPE, ATLAS_NODE extends AtlasNode<FRAME_TYPE, ATLAS_NODE>> {


    boolean checkNext();

    void next();

    FRAME_TYPE current();

}
