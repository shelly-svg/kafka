package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface ConsumingService {

    /**
     * Consumes given {@code payload}
     *
     * @param payload data to consume
     */
    void doConsume(JsonNode payload);

}
