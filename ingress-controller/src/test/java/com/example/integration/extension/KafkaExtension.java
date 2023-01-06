package com.example.integration.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaExtension implements Extension, BeforeAllCallback, AfterAllCallback {

    private final KafkaContainer kafkaContainer =
        new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.1"));

    @Override
    public void beforeAll(ExtensionContext context) {
        kafkaContainer.start();
        System.setProperty("embedded.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        //we should do nothing, testcontainers will shut down the container
    }

}