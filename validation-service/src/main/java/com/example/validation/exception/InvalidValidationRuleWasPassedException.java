package com.example.validation.exception;

/**
 * Indicates when invalid validation rule was found during schema parsing
 */
public class InvalidValidationRuleWasPassedException extends RuntimeException {

    private static final long serialVersionUID = 2378237238283232932L;

    public InvalidValidationRuleWasPassedException(String message) {
        super(message);
    }

}