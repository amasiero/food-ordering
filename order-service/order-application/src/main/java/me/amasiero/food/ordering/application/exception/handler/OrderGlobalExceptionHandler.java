package me.amasiero.food.ordering.application.exception.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import me.amasiero.food.ordering.exception.OrderDomainException;
import me.amasiero.food.ordering.exception.OrderNotFoundException;

@Slf4j
@ControllerAdvice
public class OrderGlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { OrderDomainException.class })
    public ErrorDto handleException(OrderDomainException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder()
                       .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                       .message(exception.getMessage())
                       .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { OrderDomainException.class })
    public ErrorDto handleException(OrderNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder()
                       .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                       .message(exception.getMessage())
                       .build();
    }
}
