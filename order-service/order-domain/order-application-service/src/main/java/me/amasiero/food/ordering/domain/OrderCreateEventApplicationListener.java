package me.amasiero.food.ordering.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.amasiero.food.ordering.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import me.amasiero.food.ordering.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@AllArgsConstructor
public class OrderCreateEventApplicationListener {

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    @TransactionalEventListener
    void process(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent is published for order id: {}", event.order().getId().getValue());
        orderCreatedPaymentRequestMessagePublisher.publish(event);
    }
}
