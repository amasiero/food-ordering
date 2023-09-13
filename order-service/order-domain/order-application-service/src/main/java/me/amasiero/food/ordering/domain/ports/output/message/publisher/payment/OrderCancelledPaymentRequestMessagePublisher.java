package me.amasiero.food.ordering.domain.ports.output.message.publisher.payment;

import me.amasiero.food.ordering.domain.event.publisher.DomainEventPublisher;
import me.amasiero.food.ordering.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
