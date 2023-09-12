package me.amasiero.food.ordering;

import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.entity.Restaurant;
import me.amasiero.food.ordering.event.OrderCancelledEvent;
import me.amasiero.food.ordering.event.OrderCreatedEvent;
import me.amasiero.food.ordering.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
