package ru.apzakharov.draw_processor.atlas_processor.dto;


import lombok.Builder;

@Builder
public class AnimationStep<FRAME_TYPE> {

    private final FRAME_TYPE frame;

    public FRAME_TYPE getFrame(){
        return frame;
    }
}
