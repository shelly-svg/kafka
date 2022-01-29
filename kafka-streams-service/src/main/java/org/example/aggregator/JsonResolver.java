package org.example.aggregator;

public interface JsonResolver {

    /**
     * Returns true if {@code dataToCheck} can be parsed to JsonNode object, otherwise returns false
     *
     * @param dataToCheck data to check if it can be transformed to JsonNode object
     * @return true if {@code dataToCheck} can be parsed to JsonNode object
     * @see com.fasterxml.jackson.databind.JsonNode
     */
    boolean isJson(String dataToCheck);

}
