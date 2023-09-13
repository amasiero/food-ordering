package me.amasiero.food.ordering.domain.ports.output.message.publisher.restaurantapproval;

import me.amasiero.food.ordering.domain.event.publisher.DomainEventPublisher;
import me.amasiero.food.ordering.event.OrderPaidEvent;

public interface OrderPaidRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
