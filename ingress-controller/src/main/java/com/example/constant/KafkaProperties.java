package com.example.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "bootstrap-config.kafka")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class KafkaProperties {

    private final String bootstrapServers;
    private final String producerClientId;
    private final String producerBatchSize;

}