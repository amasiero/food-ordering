package me.amasiero.food.ordering.service.messaging.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.dto.message.PaymentResponse;
import me.amasiero.food.ordering.domain.dto.message.RestaurantApprovalResponse;
import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.event.OrderCancelledEvent;
import me.amasiero.food.ordering.event.OrderCreatedEvent;
import me.amasiero.food.ordering.event.OrderPaidEvent;
import me.amasiero.food.ordering.order.avro.model.PaymentOrderStatus;
import me.amasiero.food.ordering.order.avro.model.PaymentRequestAvroModel;
import me.amasiero.food.ordering.order.avro.model.PaymentResponseAvroModel;
import me.amasiero.food.ordering.order.avro.model.RestaurantApprovalRequestAvroModel;
import me.amasiero.food.ordering.order.avro.model.RestaurantApprovalResponseAvroModel;
import me.amasiero.food.ordering.order.avro.model.RestaurantOrderStatus;

@Component
public class OrderMessagingDataMapper {

    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {
        Order order = orderCreatedEvent.order();

        return PaymentRequestAvroModel.newBuilder()
                                      .setId(UUID.randomUUID().toString())
                                      .setSagaId("")
                                      .setCustomerId(order.getCustomerId().getValue().toString())
                                      .setOrderId(order.getId().getValue().toString())
                                      .setPrice(order.getPrice().amount())
                                      .setCreatedAt(orderCreatedEvent.createdAt().toInstant())
                                      .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                                      .build();
    }

    public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent) {
        Order order = orderCancelledEvent.order();

        return PaymentRequestAvroModel.newBuilder()
                                      .setId(UUID.randomUUID().toString())
                                      .setSagaId("")
                                      .setCustomerId(order.getCustomerId().getValue().toString())
                                      .setOrderId(order.getId().getValue().toString())
                                      .setPrice(order.getPrice().amount())
                                      .setCreatedAt(orderCancelledEvent.createdAt().toInstant())
                                      .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                                      .build();
    }

    public RestaurantApprovalRequestAvroModel orderPaidEventToRestaurantApprovalRequestAvroModel(OrderPaidEvent domainEvent) {
        Order order = domainEvent.order();
        return RestaurantApprovalRequestAvroModel.newBuilder()
                                                 .setId(UUID.randomUUID().toString())
                                                 .setSagaId("")
                                                 .setOrderId(order.getId().getValue().toString())
                                                 .setProducts(order.getItems().stream().map(item ->
                                                     me.amasiero.food.ordering.order.avro.model.Product
                                                         .newBuilder()
                                                         .setId(item.getProduct().getId().getValue().toString())
                                                         .setQuantity(item.getQuantity())
                                                         .build()).toList())
                                                 .setPrice(order.getPrice().amount())
                                                 .setCreatedAt(domainEvent.createdAt().toInstant())
                                                 .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
                                                 .build();
    }

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel paymentResponseAvroModel) {
        return PaymentResponse.builder()
                              .id(paymentResponseAvroModel.getId())
                              .sagaId(paymentResponseAvroModel.getSagaId())
                              .orderId(paymentResponseAvroModel.getOrderId())
                              .paymentId(paymentResponseAvroModel.getPaymentId())
                              .customerId(paymentResponseAvroModel.getCustomerId())
                              .price(paymentResponseAvroModel.getPrice())
                              .status(me.amasiero.food.ordering.domain.valueobjects.PaymentStatus.valueOf(
                                  paymentResponseAvroModel.getPaymentStatus().name()))
                              .createdAt(paymentResponseAvroModel.getCreatedAt())
                              .failureMessages(paymentResponseAvroModel.getFailureMessages())
                              .build();
    }

    public RestaurantApprovalResponse restaurantApprovalResponseAvroModelToRestaurantApprovalResponse(RestaurantApprovalResponseAvroModel restaurantApprovalResponseAvroModel) {
        return RestaurantApprovalResponse.builder()
                                         .id(restaurantApprovalResponseAvroModel.getId())
                                         .sagaId(restaurantApprovalResponseAvroModel.getSagaId())
                                         .orderId(restaurantApprovalResponseAvroModel.getOrderId())
                                         .restaurantId(restaurantApprovalResponseAvroModel.getRestaurantId())
                                         .createdAt(restaurantApprovalResponseAvroModel.getCreatedAt())
                                         .status(me.amasiero.food.ordering.domain.valueobjects.OrderApprovalStatus.valueOf(
                                             restaurantApprovalResponseAvroModel.getOrderApprovalStatus().name()))
                                         .failureMessages(restaurantApprovalResponseAvroModel.getFailureMessages())
                                         .build();
    }
}
