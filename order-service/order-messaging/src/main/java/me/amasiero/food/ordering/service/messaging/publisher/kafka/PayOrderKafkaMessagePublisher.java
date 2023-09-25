package me.amasiero.food.ordering.service.messaging.publisher.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.config.OrderServiceConfigData;
import me.amasiero.food.ordering.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRequestMessagePublisher;
import me.amasiero.food.ordering.event.OrderPaidEvent;
import me.amasiero.food.ordering.kafka.producer.service.KafkaProducer;
import me.amasiero.food.ordering.order.avro.model.RestaurantApprovalRequestAvroModel;
import me.amasiero.food.ordering.service.messaging.mapper.OrderMessagingDataMapper;

@Slf4j
@Component
@AllArgsConstructor
public class PayOrderKafkaMessagePublisher implements OrderPaidRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.order().getId().getValue().toString();

        try {
            RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel = orderMessagingDataMapper
                .orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent);

            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                orderId,
                restaurantApprovalRequestAvroModel,
                orderKafkaMessageHelper.getKafkaCallback(
                    orderServiceConfigData.getPaymentResponseTopicName(),
                    restaurantApprovalRequestAvroModel,
                    orderId,
                    "RestaurantApprovalRequestAvroModel"
                )
            );

            log.info("RestaurantApprovalRequestAvroModel sent to Kafka for order id: {}", orderId);
        } catch (Exception e) {
            log.error("Error while sending RestaurantApprovalRequestAvroModel message to kafka with order id: {}, error: {}",
                orderId,
                e.getMessage());
        }
    }
}
