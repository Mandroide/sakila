package com.sakila.util;

import com.sakila.model.DefaultAPIError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultAPIError> handleException(MethodArgumentNotValidException ex) {
        return ExceptionAdviserHandler.methodArgumentNotValidException(ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DefaultAPIError> handleException(ConstraintViolationException ex) {
        return ExceptionAdviserHandler.constraintViolationException(ex);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<DefaultAPIError> handleException(HandlerMethodValidationException ex) {
        return ExceptionAdviserHandler.handlerMethodValidationException(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultAPIError> handleException(Exception ex) {
        return ExceptionAdviserHandler.createResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultAPIError> handleException(BadRequestException ex) {
        return ExceptionAdviserHandler.createResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
