package com.example.service.messaging.impl;

import com.example.service.messaging.Producer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer implements Producer {

    private final KafkaTemplate<String, JsonNode> kafkaTemplate;

    @Override
    public void doSend(String topic, String key, JsonNode payload) {
        kafkaTemplate.send(topic, key, payload);
    }

}