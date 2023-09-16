package me.amasiero.food.ordering.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
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

    public void pay () {
        if (status != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not in correct state for payment.");
        }
        status = OrderStatus.PAID;
    }

    public void approve() {
        if (status != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not in correct state for approval.");
        }
        status = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        if (status != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not in correct state for initialize the cancellation process.");
        }
        status = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages) {
        if (!(status == OrderStatus.CANCELLING || status == OrderStatus.PENDING)) {
            throw new OrderDomainException("Order is not in correct state for cancellation.");
        }
        status = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
        } else if (failureMessages != null) {
            this.failureMessages = failureMessages;
        }
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
