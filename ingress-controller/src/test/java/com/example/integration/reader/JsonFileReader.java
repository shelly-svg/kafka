package com.example.integration.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JsonFileReader {

    private final ObjectMapper objectMapper;

    public JsonNode read(String path) {
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            return objectMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can`t read json from the file: " + path);
        }
    }

}