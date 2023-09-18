package me.amasiero.food.ordering.application.handler;

import lombok.Builder;

@Builder
public record ErrorDto(String code, String message) {
}
