package me.amasiero.food.ordering.application.handler;

import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = { Exception.class })
    public ErrorDto handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder()
                       .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                       .message("An unexpected error occurred.")
                       .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { ValidationException.class })
    public ErrorDto handleException(ValidationException validationException) {
        if (validationException instanceof ConstraintViolationException constraintViolationException) {
            String violations = extractViolationsFromException(constraintViolationException);
            log.error(violations, validationException);
            return ErrorDto.builder()
                           .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                           .message(violations)
                           .build();
        }

        log.error(validationException.getMessage(), validationException);
        return ErrorDto.builder()
                       .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                       .message(validationException.getMessage())
                       .build();
    }

    private String extractViolationsFromException(ConstraintViolationException validationException) {
        return validationException.getConstraintViolations()
                                  .stream()
                                  .map(ConstraintViolation::getMessage)
                                  .collect(Collectors.joining("--"));
    }

}
