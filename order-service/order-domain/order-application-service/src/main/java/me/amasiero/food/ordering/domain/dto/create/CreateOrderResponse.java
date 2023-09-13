package me.amasiero.food.ordering.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import me.amasiero.food.ordering.domain.valueobjects.OrderStatus;

import java.util.UUID;

@Builder
public record CreateOrderResponse(
        @NotNull
        UUID trackingId,
        @NotNull
        OrderStatus status,
        @NotNull
        String message
) {
}
