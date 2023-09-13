package me.amasiero.food.ordering.domain.ports.input.message.listener.payment;

import me.amasiero.food.ordering.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
