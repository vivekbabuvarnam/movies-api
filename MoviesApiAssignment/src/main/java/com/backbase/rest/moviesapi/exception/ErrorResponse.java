package com.backbase.rest.moviesapi.exception;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.FieldError;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    @JsonInclude(Include.NON_EMPTY)
    private final List<FieldError> fields = new ArrayList<>();
    @JsonInclude(Include.NON_EMPTY)
    private final List<ErrorResponse> subErrors = new ArrayList<>();
    private String code;
    private String message;

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                        cv.getRootBeanClass().getSimpleName(),
                        ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                        cv.getMessage());
    }

    private void addValidationError(String object, String field, String message) {
        fields.add(new FieldError(object, field, message));
    }

    private void addValidationError(FieldError fieldError) {
        fields.add(fieldError);
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldError> getFields() {
        return fields;
    }

    public List<ErrorResponse> getSubErrors() {
        return subErrors;
    }
}
