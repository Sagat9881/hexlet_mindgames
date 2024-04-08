package ru.apzakharov.gamecore.context;

import lombok.Getter;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.gamecore.context.action.ActionVisitor;
import ru.apzakharov.gamecore.context.entites.GameEntity;
import ru.apzakharov.gamecore.input_processor.InputProcessor;

import java.util.Set;

public interface GameContext<INPUT_CONTAINER, FRAME_CONTAINER, GAME_ENTITY extends GameEntity<GAME_ENTITY>> {

    void offerInput(InputProcessor.Input<INPUT_CONTAINER> input);

   void addGameInstance(GAME_ENTITY gameInstance);

    boolean isInstanceAdded(GAME_ENTITY gameInstance);

    Pair<Integer, Integer> getGameWindowSize();

    Set<ObjectView<FRAME_CONTAINER, FRAME_CONTAINER>> buildViews();

    ObjectView<FRAME_CONTAINER, FRAME_CONTAINER> buildView(GAME_ENTITY entity);

    default void visitForAction(ActionVisitor visitor) {
        visitor.act(this);
    }

    @Getter
    class ObjectView<COLOR_CONTAINER, TEXTURE_TYPE> {
        public COLOR_CONTAINER colorCode;
        public TEXTURE_TYPE texture;
        private volatile int x1, x2;
        private volatile int y1, y2;
        private volatile int z1, z2;

        public ObjectView(COLOR_CONTAINER colorCode, TEXTURE_TYPE texture, int x1, int x2, int y1, int y2, int z1, int z2) {
            this.colorCode = colorCode;
            this.texture = texture;
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.z1 = z1;
            this.z2 = z2;

        }
    }

}
