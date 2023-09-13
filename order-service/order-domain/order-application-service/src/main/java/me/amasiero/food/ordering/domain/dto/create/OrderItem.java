package me.amasiero.food.ordering.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderItem(
        @NotNull
        UUID productId,
        @NotNull
        Integer quantity,
        @NotNull
        BigDecimal price,
        @NotNull
        BigDecimal subTotal
) {
}
