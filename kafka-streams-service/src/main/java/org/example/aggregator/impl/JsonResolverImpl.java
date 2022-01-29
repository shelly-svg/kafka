package org.example.aggregator.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.aggregator.JsonResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonResolverImpl implements JsonResolver {

    private final ObjectMapper objectMapper;

    @Override
    public boolean isJson(String dataToCheck) {
        try {
            objectMapper.readTree(dataToCheck);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

}
