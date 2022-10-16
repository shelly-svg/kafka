package com.example.validation.validator;

import java.util.Optional;

public interface CustomJsonFieldValidator {

    /**
     * Returns an {@link Optional} with error message if given {@code fieldValue} is not valid
     *
     * @param fieldValue value from the field of the entity which being validated
     * @return {@link Optional} with error message if {@code fieldValue} is not valid, otherwise an empty {@link
     * Optional}
     */
    Optional<String> getValidationError(String fieldValue);

}