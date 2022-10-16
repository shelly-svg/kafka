package com.example.stream;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class ValidationStreamProcessor {

    @Bean
    public Function<KStream<String, JsonNode>, KStream<String, JsonNode>[]> processUsersStream() {
        return processValidation();
    }

    @SuppressWarnings("unchecked")
    private Function<KStream<String, JsonNode>, KStream<String, JsonNode>[]> processValidation() {
        return usersStream -> {
            usersStream
                .peek((key, value) -> log.info("Received new message for validation with key: {}, value: {}", key,
                    value));

            Map<String, KStream<String, JsonNode>> branches = usersStream
                .split(Named.as("prefix-"))
                .branch((key, value) -> "John".equals(value.get("FirstName").asText()), Branched.as("matched"))
                .defaultBranch(Branched.as("not-matched"));

            return new KStream[]{branches.get("prefix-matched"), branches.get("prefix-not-matched")};
        };
    }

    @Bean
    public Function<KStream<String, JsonNode>, KStream<String, JsonNode>[]> processRetryUsersStream() {
        return processValidation();
    }

}