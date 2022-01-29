package org.example.aggregator;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonResolver {

    JsonNode transform(String jsonAsString);

    boolean isJson(String dataToCheck);

}
