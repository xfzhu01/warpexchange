package com.michaelzhu.exchange.messaging;

import com.michaelzhu.exchange.message.AbstractMessage;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;

@FunctionalInterface
public interface MessageProducer<T extends AbstractMessage> {
    void sendMessage(T message);

    default void sendMessages(List<T> messages) {
        for (T message : messages) {
            sendMessage(message);
        }
    }
}
