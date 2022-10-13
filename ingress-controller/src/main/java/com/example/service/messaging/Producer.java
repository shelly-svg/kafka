package com.example.service.messaging;

import com.fasterxml.jackson.databind.JsonNode;

public interface Producer {

    /**
     * Sends {@code payload} into {@code topic} with given {@code key}
     *
     * @param topic   name of the topic
     * @param key     key of the message
     * @param payload message itself
     */
    void doSend(String topic, String key, JsonNode payload);

}