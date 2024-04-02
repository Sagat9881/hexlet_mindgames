package ru.apzakharov.draw_processor.atlas_processor;

import ru.apzakharov.context.CommandLineGameContext;
import ru.apzakharov.data_structure.abstract_structure.Queue;
import ru.apzakharov.data_structure.structure.LinkedListQueue;
import ru.apzakharov.draw_processor.atlas_processor.dto.AnimationStep;
import ru.apzakharov.gamecore.draw_processor.atlas_processor.AtlasProcessor;
import ru.apzakharov.draw_processor.atlas_processor.dto.PlainStringNode;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

//TODO: Стринг уже есть в процессоре атласов
//      стоит перестроить систему дженериков так, чтоб не приходилось дублировать типы
//      AnimationStep должен получить свой тип из процессора, а не прописываться руками
public class SimpleCommandLineAtlasProcessor implements AtlasProcessor<CommandLineGameContext, String, PlainStringNode> {
    private static final long ANIMATION_SPEED = 1500L;
    private static final Long INIT_CHECK_VALUE = 0L;
    private final Queue.ListQueue<AnimationStep<String>> nodes = new LinkedListQueue<>();
    private final AtomicReference<AnimationStep<String>> currentAnimation = new AtomicReference<>(nodes.peek());
    private final AtomicLong last_check = new AtomicLong(INIT_CHECK_VALUE);

    public SimpleCommandLineAtlasProcessor(Queue.ListQueue<AnimationStep<String>> animationObjectScenario) {
        this.nodes.addAll(animationObjectScenario);

    }

    @SafeVarargs
    public SimpleCommandLineAtlasProcessor(AnimationStep<String>... nodeAr) {
        Arrays.stream(nodeAr).forEachOrdered(nodes::offer);
    }


    @Override
    public boolean checkNext() {
        final long newCheck = System.currentTimeMillis();
        return (newCheck - (last_check.getAndSet(newCheck))) > ANIMATION_SPEED;
    }

    @Override
    public void next() {
        if (checkNext()) {
            //TODO: Все равно тут не атомарно - надо сделать атомарную операцию (может класс передизайнить?)
            AnimationStep<String> objectFrame = nodes.poll();
            currentAnimation.set(objectFrame);
            last_check.set(INIT_CHECK_VALUE);

            // помещаем анимацию в начало очереди
            nodes.offer(objectFrame);
        }
    }

    @Override
    public String current() {
        return currentAnimation.get().getFrame();
    }
}
