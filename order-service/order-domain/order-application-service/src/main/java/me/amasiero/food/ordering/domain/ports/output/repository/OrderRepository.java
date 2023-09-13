package me.amasiero.food.ordering.domain.ports.output.repository;

import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.valueobjects.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findByTrackingId(TrackingId trackingId);
}
