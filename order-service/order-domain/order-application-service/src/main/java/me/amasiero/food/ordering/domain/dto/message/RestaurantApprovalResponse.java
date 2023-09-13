package me.amasiero.food.ordering.domain.dto.message;

import lombok.Builder;
import me.amasiero.food.ordering.domain.valueobjects.OrderApprovalStatus;

import java.time.Instant;
import java.util.List;

@Builder
public record RestaurantApprovalResponse(
        String id,
        String sagaId,
        String orderId,
        String restaurantId,
        Instant createdAt,
        OrderApprovalStatus status,
        List<String> failureMessages
) {
}
