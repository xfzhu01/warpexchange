package com.michaelzhu.exchange.enums;

public enum OrderStatus {

    PENDING(false),

    FULLY_FILLED(true),

    PARTIAL_FILLED(false),

    PARTIAL_CANCELLED(true),

    FULLY_CANCELLED(true);

    public final boolean isFinalStatus;
    OrderStatus(boolean isFinalStatus) {
        this.isFinalStatus = isFinalStatus;
    }
}
