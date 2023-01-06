package com.example.validation.validator;

import java.util.Optional;

public interface JsonFieldValidator {

    /**
     * Returns an {@link Optional} with error message if {@code validatedValue} is not valid corresponding to the {@code
     * validatorValue}
     *
     * @param validatorValue validation rule value
     * @param validatedValue value from the field of the entity which being validated
     * @return {@link Optional} with error message if {@code validatedValue} is not valid, otherwise an empty {@link
     * Optional}
     */
    Optional<String> getValidationError(String validatorValue, String validatedValue);

}