package ru.apzakharov.gamecore.draw_processor.atlas_processor;

import ru.apzakharov.gamecore.context.GameContext;

public interface AtlasProcessor<CONTEXT extends GameContext, FRAME_TYPE, ATLAS_NODE extends AtlasNode<FRAME_TYPE, ATLAS_NODE>> {


    boolean checkNext();

    void next();
    FRAME_TYPE current();

}
