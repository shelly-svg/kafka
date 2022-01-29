package org.example.aggregator.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.aggregator.JsonResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonResolverImpl implements JsonResolver {

    private static final String EXCEPTION_OCCURRED_WHILE_TRANSFORMING_MESSAGE = "An exception has occurred while "
            + "transforming data with jolt, cause: ";

    private final ObjectMapper objectMapper;

    @Override
    public JsonNode transform(String jsonAsString) {
        try {
            return objectMapper.readTree(jsonAsString);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(EXCEPTION_OCCURRED_WHILE_TRANSFORMING_MESSAGE + jsonAsString);
        }
    }

    @Override
    public boolean isJson(String dataToCheck) {
        try {
            transform(dataToCheck);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

}
