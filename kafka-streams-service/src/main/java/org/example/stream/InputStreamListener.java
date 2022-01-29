package org.example.stream;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.example.aggregator.JsonResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class InputStreamListener {

    private static final String RECEIVED_PAYLOAD_MESSAGE = "Received payload with key: ";

    private final JsonResolver jsonResolver;

    @Bean
    @SuppressWarnings("all")
    public Function<KStream<String, String>, KStream<String, ?>[]> processInputStream() {
        return rawDataStream -> {
            rawDataStream.peek((key, ignoredValue) -> log.trace(RECEIVED_PAYLOAD_MESSAGE + key));

            Predicate<String, String> jsonStream = (key, inputData) -> jsonResolver.isJson(inputData);
            Predicate<String, String> emptyStream =
                    (key, value) -> Objects.isNull(value) || value.isEmpty() || value.isBlank();
            Predicate<String, String> textStream = (key, rawData) -> true;
            KStream<String, String>[] branchedStreams = rawDataStream.branch(jsonStream, emptyStream, textStream);

            KStream<String, JsonNode> convertedJsonStream = convertDataInJsonStream(branchedStreams[0]);
            return new KStream[]{convertedJsonStream, branchedStreams[1], branchedStreams[2]};
        };
    }

    private KStream<String, JsonNode> convertDataInJsonStream(KStream<String, String> jsonStream) {
        return jsonStream.mapValues(jsonResolver::transform);
    }

}
