package me.amasiero.food.ordering.payment.service.domain.event;

import java.time.ZonedDateTime;
import java.util.Collections;

import me.amasiero.food.ordering.payment.service.domain.enttity.Payment;

public class PaymentCancelledEvent extends PaymentEvent {
    public PaymentCancelledEvent(Payment payment,
                                 ZonedDateTime createdAt) {
        super(payment, createdAt, Collections.emptyList());
    }
}
