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
import me.amasiero.food.ordering.exception.OrderDomainException;
import me.amasiero.food.ordering.valueobjects.OrderItemId;
import me.amasiero.food.ordering.valueobjects.StreetAddress;
import me.amasiero.food.ordering.valueobjects.TrackingId;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

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

    public void initializeOrder() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        status = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = items.stream().map(item -> {
            validateItemPrice(item);
            return item.getSubTotal();
        }).reduce(Money.ZERO, Money::add);

        if (!price.equals(orderItemsTotal)) {
            throw new OrderDomainException("Order total price does not match the sum of order items prices.");
        }
    }

    private void validateItemPrice(OrderItem item) {
        if (!item.isPriceValid()) {
            throw new OrderDomainException("Order item price is not valid.");
        }
    }

    private void validateTotalPrice() {
        if (price == null || !price.isGreaterThanZero()) {
            throw new OrderDomainException("Order total price must be greater than zero.");
        }
    }

    private void validateInitialOrder() {
        if (status != null || getId() != null) {
            throw new OrderDomainException("Order is not in correct state for initialization.");
        }
    }

    private void initializeOrderItems() {
        AtomicLong itemId = new AtomicLong(1);
        items.forEach(item -> item.initializerOrderItem(super.getId(), new OrderItemId(itemId.getAndIncrement())));
    }
}
