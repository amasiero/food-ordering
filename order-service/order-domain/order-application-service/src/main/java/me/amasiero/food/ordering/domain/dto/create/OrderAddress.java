package me.amasiero.food.ordering.domain.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record OrderAddress(
    @NonNull
    @Max(value = 50)
    String street,
    @NotNull
    @Max(value = 10)
    String postalCode,
    @NotNull
    @Max(value = 50)
    String city
) {
}
