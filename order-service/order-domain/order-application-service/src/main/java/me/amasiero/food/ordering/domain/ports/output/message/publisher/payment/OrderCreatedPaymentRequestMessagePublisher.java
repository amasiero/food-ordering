package me.amasiero.food.ordering.domain.ports.output.message.publisher.payment;

import me.amasiero.food.ordering.domain.event.publisher.DomainEventPublisher;
import me.amasiero.food.ordering.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
