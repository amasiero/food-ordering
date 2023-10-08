package me.amasiero.food.ordering.payment.service.domain.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.valueobjects.CustomerId;
import me.amasiero.food.ordering.domain.valueobjects.Money;
import me.amasiero.food.ordering.domain.valueobjects.OrderId;
import me.amasiero.food.ordering.payment.service.domain.dto.PaymentRequest;
import me.amasiero.food.ordering.payment.service.domain.enttity.Payment;

@Component
public class PaymentDataMapper {

    public Payment paymentRequestModelToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                      .orderId(new OrderId(UUID.fromString(paymentRequest.orderId())))
                      .customerId(new CustomerId(UUID.fromString(paymentRequest.customerId())))
                      .price(new Money(paymentRequest.price()))
                      .build();
    }

//    public OrderEventPayload paymentEventToOrderEventPayload(PaymentEvent paymentEvent) {
//        return OrderEventPayload.builder()
//                                .paymentId(paymentEvent.getPayment().getId().getValue().toString())
//                                .customerId(paymentEvent.getPayment().getCustomerId().getValue().toString())
//                                .orderId(paymentEvent.getPayment().getOrderId().getValue().toString())
//                                .price(paymentEvent.getPayment().getPrice().getAmount())
//                                .createdAt(paymentEvent.getCreatedAt())
//                                .paymentStatus(paymentEvent.getPayment().getPaymentStatus().name())
//                                .failureMessages(paymentEvent.getFailureMessages())
//                                .build();
//    }
}
