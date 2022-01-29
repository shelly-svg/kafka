package org.example.stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.example.aggregator.JsonResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class InputStreamListener {

    private static final String RECEIVED_PAYLOAD_MESSAGE = "Received payload with key: ";

    private final JsonResolver jsonResolver;

    @Bean
    @SuppressWarnings("unchecked")
    public Function<KStream<String, String>, KStream<String, String>[]> processInputStream() {
        return rawDataStream -> {
            rawDataStream.peek((key, ignoredValue) -> log.trace(RECEIVED_PAYLOAD_MESSAGE + key));

            Predicate<String, String> jsonStream = (key, inputData) -> jsonResolver.isJson(inputData);
            Predicate<String, String> emptyStream = (key, value) -> value.isEmpty() || value.isBlank();
            Predicate<String, String> textStream = (key, rawData) -> true;

            return rawDataStream.branch(emptyStream, jsonStream, textStream);
        };
    }

}
