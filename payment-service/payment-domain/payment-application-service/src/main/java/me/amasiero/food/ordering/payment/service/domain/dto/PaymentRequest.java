package me.amasiero.food.ordering.payment.service.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Setter;

import me.amasiero.food.ordering.order.avro.model.PaymentOrderStatus;

@Builder
public record PaymentRequest(String id,
                             String sagaId,
                             String orderId,
                             String customerId,
                             BigDecimal price,
                             Instant createdAt,
                             @Setter PaymentOrderStatus paymentOrderStatus) {

}
