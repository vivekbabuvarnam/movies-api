package com.backbase.rest.moviesapi.exception.handler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import com.backbase.rest.moviesapi.exception.ErrorResponse;
import com.backbase.rest.moviesapi.exception.InvalidJwtToken;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.backbase.rest.moviesapi.exception.NotUniqueException;
import com.backbase.rest.moviesapi.exception.ServiceConsumptionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApplicationExceptionHandler {

    /**
     * Handle validation failure exceptions which returns 500 server error
     *
     * @return response entity with internal server error status code
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("Exception: {} ", ex);
        List<FieldError> errors = ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> new FieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()))
                        .collect(Collectors.toList());
        ErrorResponse response = new ErrorResponse();
        response.setCode(HttpStatus.BAD_REQUEST.toString());
        response.setMessage("Validation errors at the DTO objects");
        response.addValidationErrors(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> customExceptionFromTheApplications(final NotFoundException ex) {
        log.error("Exception: {} ", ex);
        ErrorResponse response = new ErrorResponse();
        response.setCode(HttpStatus.NOT_FOUND.toString());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(InvalidJwtToken.class)
    public ResponseEntity<ErrorResponse> invalidJwtToken(final InvalidJwtToken ex) {
        log.error("Exception: {} ", ex);
        ErrorResponse response = new ErrorResponse();
        response.setCode(HttpStatus.NOT_FOUND.toString());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(ServiceConsumptionException.class)
    public ResponseEntity<ErrorResponse> serviceConsumptionException(final ServiceConsumptionException ex) {
        log.error("Exception: {} ", ex);
        ErrorResponse response = new ErrorResponse();
        response.setCode(HttpStatus.NOT_FOUND.toString());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(NotUniqueException.class)
    public ResponseEntity<ErrorResponse> customExceptionFromTheApplications(final NotUniqueException ex) {
        log.error("Exception: {} ", ex);
        ErrorResponse response = new ErrorResponse();
        response.setCode(HttpStatus.FORBIDDEN.toString());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

    }

    /**
     * Handle validation failure exceptions which returns 500 server error
     *
     * @return response entity with internal server error status code
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Exception: {} ", ex);
        ErrorResponse response = new ErrorResponse();
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        response.setMessage("Oops! Something went wrong!");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle validation failure exceptions which returns 400 server error
     *
     * @return response entity with internal server error status code
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Exception: {} ", ex);
        ErrorResponse response = new ErrorResponse();
        response.setCode(HttpStatus.BAD_REQUEST.toString());
        response.setMessage(ex.getMessage());
        response.addValidationErrors(ex.getConstraintViolations());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
