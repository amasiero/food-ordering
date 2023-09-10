package me.amasiero.food.ordering.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.amasiero.food.ordering.domain.entity.AggregateRoot;
import me.amasiero.food.ordering.domain.valueobjects.CustomerId;
import me.amasiero.food.ordering.domain.valueobjects.Money;
import me.amasiero.food.ordering.domain.valueobjects.OrderId;
import me.amasiero.food.ordering.domain.valueobjects.OrderStatus;
import me.amasiero.food.ordering.domain.valueobjects.RestaurantId;
import me.amasiero.food.ordering.valueobjects.StreetAddress;
import me.amasiero.food.ordering.valueobjects.TrackingId;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus status;
    private List<String> failureMessages;
}
