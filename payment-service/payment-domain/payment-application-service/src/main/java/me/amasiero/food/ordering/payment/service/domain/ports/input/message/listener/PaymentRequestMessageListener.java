package me.amasiero.food.ordering.payment.service.domain.ports.input.message.listener;

import me.amasiero.food.ordering.payment.service.domain.dto.PaymentRequest;

public interface PaymentRequestMessageListener {
    void completePayment(PaymentRequest paymentRequest);

    void cancelPayment(PaymentRequest paymentRequest);
}
