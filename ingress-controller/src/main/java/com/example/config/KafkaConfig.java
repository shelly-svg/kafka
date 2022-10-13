package com.example.config;

import com.example.constant.KafkaProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private static final String ACKS_ALL_PRODUCER_PROPERTY = "all";

    private final KafkaProperties kafkaProperties;

    @Bean
    protected KafkaTemplate<String, JsonNode> kafkaTemplate() {
        KafkaTemplate<String, JsonNode> template = new KafkaTemplate<>(producerFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    protected ProducerFactory<String, JsonNode> producerFactory() {
        Map<String, Object> kafkaConfigs = new HashMap<>();
        kafkaConfigs.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getProducerClientId());
        kafkaConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        kafkaConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        kafkaConfigs.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.getProducerBatchSize());
        kafkaConfigs.put(ProducerConfig.ACKS_CONFIG, ACKS_ALL_PRODUCER_PROPERTY);
        return new DefaultKafkaProducerFactory<>(kafkaConfigs);
    }

}