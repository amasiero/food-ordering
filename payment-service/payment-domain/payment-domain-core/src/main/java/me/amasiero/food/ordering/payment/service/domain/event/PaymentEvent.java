package me.amasiero.food.ordering.payment.service.domain.event;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import me.amasiero.food.ordering.domain.event.DomainEvent;
import me.amasiero.food.ordering.payment.service.domain.enttity.Payment;

@Getter
@AllArgsConstructor
public abstract class PaymentEvent implements DomainEvent<Payment> {
    private final Payment payment;
    private final ZonedDateTime createdAt;
    private final List<String> failureMessages;
}
