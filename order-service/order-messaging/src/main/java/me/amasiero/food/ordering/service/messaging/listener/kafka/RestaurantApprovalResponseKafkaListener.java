package me.amasiero.food.ordering.service.messaging.listener.kafka;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import me.amasiero.food.ordering.kafka.consumer.KafkaConsumer;
import me.amasiero.food.ordering.order.avro.model.OrderApprovalStatus;
import me.amasiero.food.ordering.order.avro.model.RestaurantApprovalResponseAvroModel;
import me.amasiero.food.ordering.service.messaging.mapper.OrderMessagingDataMapper;

import static me.amasiero.food.ordering.entity.Order.FAILURE_MESSAGES_DELIMITER;

@Slf4j
@Component
@AllArgsConstructor
public class RestaurantApprovalResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseAvroModel> {

    private final RestaurantApprovalResponseMessageListener restaurantApprovalResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    @Override
    @KafkaListener(
        id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
        topics = "${order-service.restaurant-approval-response-topic-name}"
    )
    public void receive(
        @Payload List<RestaurantApprovalResponseAvroModel> messages,
        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
        @Header(KafkaHeaders.OFFSET) List<Long> offsets
    ) {
        log.info("{} number of restaurant approval response messages received from kafka with keys: {}, partitions: {} and offsets: {}",
            messages.size(),
            keys.toString(),
            partitions.toString(),
            offsets.toString()
        );

        messages.forEach(restaurantApprovalResponseAvroModel -> {
            if (OrderApprovalStatus.APPROVED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()) {
                log.info("Processing restaurant approval for order id: {}", restaurantApprovalResponseAvroModel.getOrderId());
                restaurantApprovalResponseMessageListener.orderApproved(
                    orderMessagingDataMapper.restaurantApprovalResponseAvroModelToRestaurantApprovalResponse(restaurantApprovalResponseAvroModel)
                );
            } else if (OrderApprovalStatus.REJECTED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()) {
                log.info("Processing restaurant rejection for order id: {}, with failure messages: {}",
                    restaurantApprovalResponseAvroModel.getOrderId(),
                    String.join(FAILURE_MESSAGES_DELIMITER, restaurantApprovalResponseAvroModel.getFailureMessages())
                );
                restaurantApprovalResponseMessageListener.orderRejected(
                    orderMessagingDataMapper.restaurantApprovalResponseAvroModelToRestaurantApprovalResponse(restaurantApprovalResponseAvroModel)
                );
            }

        });
    }
}
