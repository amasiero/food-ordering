package me.amasiero.food.ordering;

import lombok.extern.slf4j.Slf4j;
import me.amasiero.food.ordering.domain.exception.DomainException;
import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.entity.Product;
import me.amasiero.food.ordering.entity.Restaurant;
import me.amasiero.food.ordering.event.OrderCancelledEvent;
import me.amasiero.food.ordering.event.OrderCreatedEvent;
import me.amasiero.food.ordering.event.OrderPaidEvent;
import me.amasiero.food.ordering.exception.OrderDomainException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    private static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order {} is initialized.", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order {} is paid.", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order {} is approved.", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancelling for the one with id: {}", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order {} is cancelled.", order.getId().getValue());
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException(String.format(
                    "Restaurant with id: [%s] is not active.",
                    restaurant.getId().getValue()
            ));
        }
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(item ->  restaurant.getProducts().forEach(product -> {
            Product currentProduct = item.getProduct();
            if (currentProduct.equals(product)) {
                currentProduct.updateWithNameAndPrice(product.getName(), product.getPrice());
            }
        }));
    }

}
