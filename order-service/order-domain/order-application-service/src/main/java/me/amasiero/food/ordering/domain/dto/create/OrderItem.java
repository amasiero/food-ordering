package me.amasiero.food.ordering.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import me.amasiero.food.ordering.domain.valueobjects.Money;

import java.util.UUID;

@Builder
public record OrderItem(
        @NotNull
        UUID productId,
        @NotNull
        Integer quantity,
        @NotNull
        Money price,
        @NotNull
        Money subTotal
) {
}
