package ru.apzakharov.draw_processor.atlas_processor.dto;

import lombok.Getter;
import ru.apzakharov.gamecore.draw_processor.AbstractAtlasNode;

@Getter
public class PlainStringNode extends AbstractAtlasNode<String, PlainStringNode> {
    final String frame;

    public PlainStringNode(String frame, PlainStringNode next, PlainStringNode prev) {
        super(next, prev, frame);
        this.frame = frame;
    }


}
