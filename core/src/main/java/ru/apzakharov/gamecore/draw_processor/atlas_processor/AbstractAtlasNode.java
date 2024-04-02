package ru.apzakharov.gamecore.draw_processor.atlas_processor;


public abstract class AbstractAtlasNode<FRAME_TYPE, NODE extends AbstractAtlasNode<FRAME_TYPE, NODE>>
        implements AtlasNode<FRAME_TYPE, NODE> {
    final NODE next;
    final NODE prev;

    final FRAME_TYPE frameType;

    protected AbstractAtlasNode(NODE next, NODE prev, FRAME_TYPE frameType) {
        this.next = next;
        this.prev = prev;
        this.frameType = frameType;
    }

    public FRAME_TYPE getFrameType() {
        return frameType;
    }
    @Override
    public NODE getNext() {
        return next;
    }

    @Override
    public NODE getPrev() {
        return prev;
    }
}
