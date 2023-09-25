package me.amasiero.food.ordering.service.messaging.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.event.OrderCancelledEvent;
import me.amasiero.food.ordering.event.OrderCreatedEvent;
import me.amasiero.food.ordering.event.OrderPaidEvent;
import me.amasiero.food.ordering.order.avro.model.PaymentOrderStatus;
import me.amasiero.food.ordering.order.avro.model.PaymentRequestAvroModel;
import me.amasiero.food.ordering.order.avro.model.RestaurantApprovalRequestAvroModel;
import me.amasiero.food.ordering.order.avro.model.RestaurantOrderStatus;

@Component
public class OrderMessagingDataMapper {

    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {
        Order order = orderCreatedEvent.order();

        return PaymentRequestAvroModel.newBuilder()
                                      .setId(UUID.randomUUID())
                                      .setSagaId(null)
                                      .setCustomerId(order.getCustomerId().getValue())
                                      .setOrderId(order.getId().getValue())
                                      .setPrice(order.getPrice().amount())
                                      .setCreatedAt(orderCreatedEvent.createdAt().toInstant())
                                      .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                                      .build();
    }

    public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent) {
        Order order = orderCancelledEvent.order();

        return PaymentRequestAvroModel.newBuilder()
                                      .setId(UUID.randomUUID())
                                      .setSagaId(null)
                                      .setCustomerId(order.getCustomerId().getValue())
                                      .setOrderId(order.getId().getValue())
                                      .setPrice(order.getPrice().amount())
                                      .setCreatedAt(orderCancelledEvent.createdAt().toInstant())
                                      .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                                      .build();
    }

    public RestaurantApprovalRequestAvroModel orderPaidEventToRestaurantApprovalRequestAvroModel(OrderPaidEvent domainEvent) {
        Order order = domainEvent.order();
        return RestaurantApprovalRequestAvroModel.newBuilder()
                                                 .setId(UUID.randomUUID())
                                                 .setSagaId(null)
                                                 .setOrderId(order.getId().getValue())
                                                 .setProducts(order.getItems().stream().map(item ->
                                                     me.amasiero.food.ordering.order.avro.model.Product
                                                         .builder()
                                                         .id(item.getProduct().getId().getValue())
                                                         .quantity(item.getQuantity())
                                                         .build()).toList())
                                                 .setPrice(order.getPrice().amount())
                                                 .setCreatedAt(domainEvent.createdAt().toInstant())
                                                 .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
                                                 .build();
    }
}
