package me.amasiero.food.ordering.domain.mapper;

import me.amasiero.food.ordering.domain.dto.create.CreateOrderCommand;
import me.amasiero.food.ordering.domain.dto.create.CreateOrderResponse;
import me.amasiero.food.ordering.domain.dto.create.OrderAddress;
import me.amasiero.food.ordering.domain.valueobjects.CustomerId;
import me.amasiero.food.ordering.domain.valueobjects.Money;
import me.amasiero.food.ordering.domain.valueobjects.ProductId;
import me.amasiero.food.ordering.domain.valueobjects.RestaurantId;
import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.entity.OrderItem;
import me.amasiero.food.ordering.entity.Product;
import me.amasiero.food.ordering.entity.Restaurant;
import me.amasiero.food.ordering.valueobjects.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .id(new RestaurantId(createOrderCommand.restaurantId()))
                .products(createOrderCommand.items()
                        .stream()
                        .map(item -> Product.builder()
                                .id(new ProductId(item.productId()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.customerId()))
                .restaurantId(new RestaurantId(createOrderCommand.restaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.address()))
                .price(new Money(createOrderCommand.price()))
                .items(orderItemsToOrderItems(createOrderCommand.items()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {
        return CreateOrderResponse.builder()
                .trackingId(order.getTrackingId().getValue())
                .status(order.getStatus())
                .build();
    }

    private List<OrderItem> orderItemsToOrderItems(List<me.amasiero.food.ordering.domain.dto.create.OrderItem> items) {
        return items.stream()
                .map(item -> OrderItem.builder()
                        .product(Product.builder()
                                .id(new ProductId(item.productId()))
                                .build())
                        .price(new Money(item.price()))
                        .quantity(item.quantity())
                        .subTotal(new Money(item.subTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress address) {
        return new StreetAddress(
                UUID.randomUUID(),
                address.street(),
                address.postalCode(),
                address.city()
        );
    }
}
