package ru.apzakharov.data_structure.structure;

import ru.apzakharov.data_structure.abstract_structure.Pair;

public class PairImpl<RIGHT, LEFT> implements Pair<RIGHT, LEFT> {

    private final RIGHT rightValue;
    private final LEFT leftValue;

    public PairImpl(RIGHT rightValue, LEFT leftValue) {
        this.rightValue = rightValue;
        this.leftValue = leftValue;
    }

    public RIGHT getRight() {
        return rightValue;
    }

    public LEFT getLeft() {
        return leftValue;
    }
}
