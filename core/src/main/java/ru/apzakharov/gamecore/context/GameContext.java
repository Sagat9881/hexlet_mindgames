package ru.apzakharov.gamecore.context;

import lombok.Getter;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.gamecore.action.ActionVisitor;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

public interface GameContext {

    void offerInput(InputProcessor.Input input);

    Pair<Integer, Integer> getGameWindowSize();

    ObjectView buildView();

    default void visitForAction(ActionVisitor visitor) {
        if (visitor.isActionExist(this)) {
            visitor.act(this);
        }
    }

    @Getter
    class ObjectView<COLOR_CONTAINER> {
        public COLOR_CONTAINER colorCode;

        public ObjectView(COLOR_CONTAINER colorCode) {
            this.colorCode = colorCode;
        }
    }

}
