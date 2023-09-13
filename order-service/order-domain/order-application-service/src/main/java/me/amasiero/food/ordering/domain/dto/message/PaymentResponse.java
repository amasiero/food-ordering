package me.amasiero.food.ordering.domain.dto.message;

import lombok.Builder;
import me.amasiero.food.ordering.domain.valueobjects.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
public record PaymentResponse(
        String id,
        String sagaId,
        String orderId,
        String paymentId,
        String customerId,
        BigDecimal price,
        Instant createdAt,
        PaymentStatus status,
        List<String> failureMessages
) {
}
