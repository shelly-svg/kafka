package com.example.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

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