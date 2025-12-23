package com.michaelzhu.exchange.messaging;

import com.michaelzhu.exchange.message.AbstractMessage;

import java.util.List;

@FunctionalInterface
public interface BatchMessageHandler<T extends AbstractMessage> {
    void processMessages(List<T> messages);
}
