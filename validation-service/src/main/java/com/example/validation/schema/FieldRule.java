package com.example.validation.schema;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.EnumMap;
import java.util.Map;

/**
 * Contains all validation rules for specific JSON field
 */
@RequiredArgsConstructor
@Getter
@ToString
public class FieldRule {

    private final String field;
    private final Map<ValidationRule, String> validationRules = new EnumMap<>(ValidationRule.class);

    public void add(ValidationRule rule, String ruleValue) {
        validationRules.put(rule, ruleValue);
    }

}