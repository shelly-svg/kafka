package com.example.integration.config;

import com.example.constant.TestConstants;
import com.example.integration.consumer.KafkaListener;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Properties;
import java.util.Set;

@TestConfiguration
@RequiredArgsConstructor
public class ListenerConfig {

    private final Environment environment;

    @Value("${bootstrap-config.kafka.topic.target}")
    private String targetTopic;
    @Value("${bootstrap-config.kafka.poll-interval-ms}")
    private int pollIntervalMs;

    @Bean
    protected KafkaListener kafkaListener() {
        return new KafkaListener(createConsumerProperties(), Set.of(targetTopic), pollIntervalMs);
    }

    private Properties createConsumerProperties() {
        var consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, TestConstants.CONSUMER_CLIENT_ID);
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            environment.getProperty("bootstrap-config.kafka.bootstrap-servers"));
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        consumerProperties.put(JsonDeserializer.TRUSTED_PACKAGES, "com.fasterxml.jackson.databind.*");
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, TestConstants.CONSUMER_GROUP_ID);
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return consumerProperties;
    }

}