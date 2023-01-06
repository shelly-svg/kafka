package com.example.validation.schema;

import com.example.validation.exception.InvalidValidationRuleWasPassedException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

@Getter
public class Schema {

    private static final String REQUIRED_FIELDS_NODE = "required";
    private static final String FIELDS_NODE = "fields";
    private static final String FIELD_NAME_NODE = "field_name";
    private static final String RULES_NODE = "rules";

    private final Set<String> requiredFields;
    private final Set<FieldRule> fieldRules;

    private Schema(Set<String> requiredFields, Set<FieldRule> fieldRules) {
        this.requiredFields = requiredFields;
        this.fieldRules = fieldRules;
    }

    /**
     * Returns a validation schema built from json
     *
     * @param schema validation schema as json object
     * @return {@link Schema}
     */
    public static Schema fromJson(JsonNode schema) {
        return new Schema(getRequiredFields(schema), getFieldRules(schema));
    }

    /**
     * Returns a Set of required fields for given {@code schema}
     *
     * @param schema validation schema as json object
     * @return {@link Set} with required fields names
     */
    private static Set<String> getRequiredFields(JsonNode schema) {
        Set<String> requiredFields = new HashSet<>();
        var requiredFieldsNode = schema.get(REQUIRED_FIELDS_NODE);

        if (requiredFieldsNode != null) {
            for (var requiredField : requiredFieldsNode) {
                requiredFields.add(requiredField.asText());
            }
        }

        return requiredFields;
    }

    /**
     * Returns a Set of field rules for given {@code schema}
     *
     * @param schema validation schema as json object
     * @return {@link Set} of {@link FieldRule} objects
     * @see FieldRule
     */
    private static Set<FieldRule> getFieldRules(JsonNode schema) {
        Set<FieldRule> fieldRules = new HashSet<>();
        var fieldRulesNode = schema.get(FIELDS_NODE);

        if (fieldRulesNode != null) {
            for (var fieldNode : fieldRulesNode) {
                fieldRules.add(getFieldRule(fieldNode));
            }
        }

        return fieldRules;
    }

    /**
     * Returns {@link FieldRule} with all rules for specific field
     *
     * @param fieldRulesNode json object which contains field rules
     * @return {@link FieldRule}
     */
    private static FieldRule getFieldRule(JsonNode fieldRulesNode) {
        var fieldRule = new FieldRule(fieldRulesNode.get(FIELD_NAME_NODE).asText());

        Iterator<Entry<String, JsonNode>> iterator = fieldRulesNode.get(RULES_NODE).fields();
        while (iterator.hasNext()) {
            addRule(fieldRule, iterator.next());
        }

        return fieldRule;
    }

    /**
     * Adds {@code rule} to the {@code fieldRule}
     *
     * @param fieldRule field rule
     * @param rule      entry of the rule, where the key is a rule name, and the value is a rule value
     * @throws com.example.validation.exception.InvalidValidationRuleWasPassedException if there is no rule with {@code
     *                                                                                  rule.getKey()} name
     */
    private static void addRule(FieldRule fieldRule, Entry<String, JsonNode> rule) {
        try {
            fieldRule.add(ValidationRule.valueOf(rule.getKey().toUpperCase()), rule.getValue().asText());
        } catch (IllegalArgumentException exception) {
            throw new InvalidValidationRuleWasPassedException(
                "Invalid validation rule was found, rule name: " + rule.getKey());
        }
    }

}