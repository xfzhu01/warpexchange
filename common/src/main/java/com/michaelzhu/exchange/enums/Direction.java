package com.michaelzhu.exchange.enums;

public enum Direction {
    BUY(1),

    SELL(0);

    public final int value;

    Direction(int value) {
        this.value = value;
    }

    public Direction negate() {
        return this == BUY ? SELL : BUY;
    }

    public static Direction of(int value) {
        if (value == 1) {
            return BUY;
        }
        if (value == 0) {
            return SELL;
        }

        throw new IllegalArgumentException("Invalid direction value.");
    }
}
