package com.example.stream;

import com.example.stream.wrapper.SplitStream;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

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

            Predicate<String, JsonNode> predicate = (key, value) -> "John".equals(value.get("FirstName").asText());

            var splitStream = SplitStream.split(usersStream, predicate);

            return new KStream[]{splitStream.getMatchedStream(), splitStream.getNonMatchedStream()};
        };
    }

    @Bean
    public Function<KStream<String, JsonNode>, KStream<String, JsonNode>[]> processRetryUsersStream() {
        return processValidation();
    }

}