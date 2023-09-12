package me.amasiero.food.ordering.event;

import me.amasiero.food.ordering.domain.event.DomainEvent;
import me.amasiero.food.ordering.entity.Order;

import java.time.ZonedDateTime;

public record OrderCreatedEvent(Order order, ZonedDateTime createdAt) implements DomainEvent<Order> {
}
