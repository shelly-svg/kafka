package com.example.validation.validator;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public interface JsonValidator {

    /**
     * Returns true if given {@code entity} is valid
     *
     * @param entity entity to validate
     * @return true if given {@code entity} is valid, otherwise false
     */
    boolean isValid(JsonNode entity);

    /**
     * Returns a Map of validation errors for given {@code entity}.The key is name of the invalid field, and the value
     * is an error description
     *
     * @param entity entity to validate
     * @return {@link Map} of validation errors
     */
    Map<String, String> getValidationErrors(JsonNode entity);

}