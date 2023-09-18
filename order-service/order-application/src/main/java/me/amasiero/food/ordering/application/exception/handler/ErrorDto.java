package me.amasiero.food.ordering.application.exception.handler;

import lombok.Builder;

@Builder
public record ErrorDto(String code, String message) {
}
