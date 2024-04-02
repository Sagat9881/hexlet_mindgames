package ru.apzakharov.gamecore.draw_processor.atlas_processor;

public interface AtlasNode<FRAME_TYPE, NODE extends AtlasNode<FRAME_TYPE, NODE>> {
    NODE getNext();

    NODE getPrev();

    FRAME_TYPE getFrameType();
}
