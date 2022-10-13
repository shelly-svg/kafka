package com.example.unit.service.messaging.impl;

import com.example.constant.TestConstants;
import com.example.reader.JsonFileReader;
import com.example.service.messaging.Producer;
import com.example.service.messaging.impl.KafkaProducer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
class KafkaProducerTest {

    private static final String TOPIC = "test-topic";
    private static final String KEY = "test-key";

    private final JsonFileReader fileReader = new JsonFileReader(new ObjectMapper());
    private final KafkaTemplate<String, JsonNode> kafkaTemplate = mock(KafkaTemplate.class);

    private final Producer producer = new KafkaProducer(kafkaTemplate);

    @Test
    void doSend_shouldSendGivenPayload_whenCalled() {
        var payload = fileReader.read(TestConstants.PATH_TO_USER);

        producer.doSend(TOPIC, KEY, payload);

        verify(kafkaTemplate).send(TOPIC, KEY, payload);
    }

}