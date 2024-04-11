package math_game.entites;

import ru.apzakharov.context.entites.ConsoleGameEntity;
import ru.apzakharov.context.entites.Rectangle2D;
import ru.apzakharov.data_structure.abstract_structure.Pair;
import ru.apzakharov.draw_processor.atlas_processor.SimpleCommandLineAtlasProcessor;

public class TaskDescription extends Rectangle2D {
    private final String taskDescription;

    public TaskDescription(
              SimpleCommandLineAtlasProcessor<ConsoleGameEntity> atlasProcessor
            , String colorCode
            , String taskDescription
            , Pair<Integer, Integer> y0_y1
            , Pair<Integer, Integer> x0_x1) {

        super(atlasProcessor, colorCode);
        this.taskDescription = taskDescription;
        this.setY0_y1(y0_y1);
        this.setX0_x1(x0_x1);
    }

    @Override
    public String getTexture() {
        return taskDescription;
    }
}
