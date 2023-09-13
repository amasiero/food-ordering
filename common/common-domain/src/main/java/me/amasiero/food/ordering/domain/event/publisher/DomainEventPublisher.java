package me.amasiero.food.ordering.domain.event.publisher;

import me.amasiero.food.ordering.domain.event.DomainEvent;

public interface DomainEventPublisher <T extends DomainEvent<?>> {
    void publish(T domainEvent);
}
