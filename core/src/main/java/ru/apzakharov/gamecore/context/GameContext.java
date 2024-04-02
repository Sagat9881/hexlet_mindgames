package ru.apzakharov.gamecore.context;

import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.gamecore.action.ActionVisitor;
import ru.apzakharov.gamecore.input_processor.Input;

public interface GameContext {

    void offerInput(Input input);

    Pair<Integer, Integer> getGameWindowSize();

    ObjectView buildView();

    default void visitForAction(ActionVisitor visitor) {
        if (visitor.isActionExist(this)) {
            visitor.act(this);
        }
    }


   class ObjectView<COLOR_CONTAINER> {
        final private COLOR_CONTAINER colorCode;

        public ObjectView(COLOR_CONTAINER colorCode) {
            this.colorCode = colorCode;
        }
    }

}
