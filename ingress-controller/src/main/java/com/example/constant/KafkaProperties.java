package com.example.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bootstrap-config.kafka")
@RequiredArgsConstructor
@Getter
public class KafkaProperties {

    private final String bootstrapServers;
    private final String producerClientId;
    private final String producerBatchSize;

}