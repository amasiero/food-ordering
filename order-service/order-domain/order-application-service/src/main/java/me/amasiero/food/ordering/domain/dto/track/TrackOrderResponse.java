package me.amasiero.food.ordering.domain.dto.track;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import me.amasiero.food.ordering.domain.valueobjects.OrderStatus;

import java.util.List;
import java.util.UUID;

@Builder
public record TrackOrderResponse(
        @NotNull
        UUID trackingId,
        @NotNull
        OrderStatus status,
        List<String> failureMessages
) {
}
