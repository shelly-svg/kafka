package com.example.integration.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KafkaListener {

    private final KafkaConsumer<String, JsonNode> kafkaConsumer;
    private final int pollIntervalMs;

    @Getter
    private final Queue<JsonNode> payloads = new ConcurrentLinkedQueue<>();

    public KafkaListener(Properties consumerProperties, Set<String> sourceTopics, int pollIntervalMs) {
        kafkaConsumer = new KafkaConsumer<>(consumerProperties);
        kafkaConsumer.subscribe(sourceTopics);
        this.pollIntervalMs = pollIntervalMs;
    }

    public void start() {
        while (!Thread.currentThread().isInterrupted()) {
            var consumerRecords = kafkaConsumer.poll(Duration.ofMillis(pollIntervalMs));
            for (var record : consumerRecords) {
                payloads.add(record.value());
            }
        }
    }

}