package com.example.stream;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class PersistenceStreamProcessor {

    @Bean
    public Consumer<KStream<String, JsonNode>> processValidUsersStream() {
        return validUsersStream -> validUsersStream
            .peek((key, value) -> log.info("Received new message to persist with key: {}, and value: {}", key, value));
    }

}