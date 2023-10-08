package me.amasiero.food.ordering.payment.service.domain.ports.output.repository;

import java.util.Optional;
import java.util.UUID;

import me.amasiero.food.ordering.payment.service.domain.enttity.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> findByOrderId(UUID orderId);
}

