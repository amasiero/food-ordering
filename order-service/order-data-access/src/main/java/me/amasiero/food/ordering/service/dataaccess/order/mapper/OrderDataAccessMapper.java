package me.amasiero.food.ordering.service.dataaccess.order.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.valueobjects.CustomerId;
import me.amasiero.food.ordering.domain.valueobjects.Money;
import me.amasiero.food.ordering.domain.valueobjects.OrderId;
import me.amasiero.food.ordering.domain.valueobjects.ProductId;
import me.amasiero.food.ordering.domain.valueobjects.RestaurantId;
import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.entity.OrderItem;
import me.amasiero.food.ordering.entity.Product;
import me.amasiero.food.ordering.service.dataaccess.order.entity.OrderAddressEntity;
import me.amasiero.food.ordering.service.dataaccess.order.entity.OrderEntity;
import me.amasiero.food.ordering.service.dataaccess.order.entity.OrderItemEntity;
import me.amasiero.food.ordering.valueobjects.OrderItemId;
import me.amasiero.food.ordering.valueobjects.StreetAddress;
import me.amasiero.food.ordering.valueobjects.TrackingId;

import static me.amasiero.food.ordering.entity.Order.FAILURE_MESSAGES_DELIMITER;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                                             .id(order.getId().getValue())
                                             .customerId(order.getCustomerId().getValue())
                                             .restaurantId(order.getRestaurantId().getValue())
                                             .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                                             .price(order.getPrice().amount())
                                             .items(orderItemsToOrderItemEntities(order.getItems()))
                                             .status(order.getStatus())
                                             .failureMessages(order.getFailureMessages() != null ?
                                                 String.join(FAILURE_MESSAGES_DELIMITER, order.getFailureMessages()) : "")
                                             .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(item -> item.setOrder(orderEntity));

        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                    .id(new OrderId(orderEntity.getId()))
                    .customerId(new CustomerId(orderEntity.getCustomerId()))
                    .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                    .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                    .price(new Money(orderEntity.getPrice()))
                    .items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                    .trackingId(new TrackingId(orderEntity.getTrackingId()))
                    .status(orderEntity.getStatus())
                    .failureMessages(orderEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(List.of(orderEntity.getFailureMessages().split(FAILURE_MESSAGES_DELIMITER))))
                    .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream()
                    .map(this::orderItemEntityToOrderItem)
                    .toList();
    }

    private OrderItem orderItemEntityToOrderItem(OrderItemEntity orderItemEntity) {
        return OrderItem.builder()
                        .id(new OrderItemId(orderItemEntity.getId()))
                        .product(Product.builder()
                                        .id(new ProductId(orderItemEntity.getProductId()))
                                        .build())
                        .price(new Money(orderItemEntity.getPrice()))
                        .quantity(orderItemEntity.getQuantity())
                        .subTotal(new Money(orderItemEntity.getSubTotal()))
                        .build();
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return StreetAddress.builder()
                            .id(address.getId())
                            .street(address.getStreet())
                            .postalCode(address.getPostalCode())
                            .city(address.getCity())
                            .build();
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream()
                    .map(this::orderItemToOrderItemEntity)
                    .toList();
    }

    private OrderItemEntity orderItemToOrderItemEntity(OrderItem orderItem) {
        return OrderItemEntity.builder()
                              .id(orderItem.getId().getValue())
                              .productId(orderItem.getProduct().getId().getValue())
                              .price(orderItem.getPrice().amount())
                              .quantity(orderItem.getQuantity())
                              .subTotal(orderItem.getSubTotal().amount())
                              .build();
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        return OrderAddressEntity.builder()
                                 .id(deliveryAddress.id())
                                 .street(deliveryAddress.street())
                                 .city(deliveryAddress.city())
                                 .postalCode(deliveryAddress.postalCode())
                                 .build();
    }
}
