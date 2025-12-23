package com.michaelzhu.exchange.message.event;

import com.michaelzhu.exchange.enums.Direction;

import java.math.BigDecimal;

public class OrderRequestEvent extends AbstractEvent {
    public Long userId;

    public Direction direction;

    public BigDecimal price;

    public BigDecimal quantity;

    @Override
    public String toString() {
        return "OrderRequestEvent [sequenceId=" + sequenceId + ", previousId=" + previousId + ", uniqueId=" + uniqueId
                + ", refId=" + refId + ", createdAt=" + createdAt + ", userId=" + userId + ", direction=" + direction
                + ", price=" + price + ", quantity=" + quantity + "]";
    }
}
