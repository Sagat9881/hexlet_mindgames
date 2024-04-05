package ru.apzakharov.gamecore.context;

import lombok.Getter;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.gamecore.action.ActionVisitor;
import ru.apzakharov.gamecore.draw_processor.DrawProcessor;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.util.Set;

public interface GameContext<INPUT_CONTAINER, FRAME_CONTAINER> {

    void offerInput(InputProcessor.Input<INPUT_CONTAINER> input);

    void addContextObject(GameObject object);

    Pair<Integer, Integer> getGameWindowSize();

    Set<ObjectView<FRAME_CONTAINER>> buildViews();

    default void visitForAction(ActionVisitor visitor) {
        if (visitor.isActionExist(this)) {
            visitor.act(this);
        }
    }

    @Getter
    class ObjectView<COLOR_CONTAINER> {
        public COLOR_CONTAINER colorCode;
        private volatile int x1, x2;
        private volatile int y1, y2;
        private volatile int z1, z2;

        public ObjectView(COLOR_CONTAINER colorCode, int x1, int x2, int y1, int y2, int z1, int z2) {
            this.colorCode = colorCode;
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.z1 = z1;
            this.z2 = z2;

        }
    }

}
