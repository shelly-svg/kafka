package com.example.service.impl;

import com.example.service.ConsumingService;
import com.example.service.messaging.Producer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserConsumingService implements ConsumingService {

    private final Producer kafkaProducer;

    @Value("${bootstrap-config.kafka.topic.target}")
    private String targetTopic;

    @Override
    public void doConsume(JsonNode payload) {
        kafkaProducer.doSend(targetTopic, UUID.randomUUID().toString(), payload);
    }

}