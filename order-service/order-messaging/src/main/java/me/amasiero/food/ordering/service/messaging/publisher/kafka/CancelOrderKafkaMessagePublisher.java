package me.amasiero.food.ordering.service.messaging.publisher.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.config.OrderServiceConfigData;
import me.amasiero.food.ordering.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import me.amasiero.food.ordering.event.OrderCancelledEvent;
import me.amasiero.food.ordering.kafka.producer.service.KafkaProducer;
import me.amasiero.food.ordering.order.avro.model.PaymentRequestAvroModel;
import me.amasiero.food.ordering.service.messaging.mapper.OrderMessagingDataMapper;

@Slf4j
@Component
@AllArgsConstructor
public class CancelOrderKafkaMessagePublisher implements OrderCancelledPaymentRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    @Override
    public void publish(OrderCancelledEvent domainEvent) {
        String orderId = domainEvent.order().getId().getValue().toString();
        log.info("Received order cancelled event for order id: {}", orderId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper
                .orderCancelledEventToPaymentRequestAvroModel(domainEvent);

            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                orderId,
                paymentRequestAvroModel,
                orderKafkaMessageHelper.getKafkaCallback(
                    orderServiceConfigData.getPaymentResponseTopicName(),
                    paymentRequestAvroModel,
                    orderId,
                    "PaymentRequestAvroModel"
                )
            );

            log.info("PaymentRequestAvroModel sent to Kafka for order id: {}", paymentRequestAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel message to kafka with order id: {}, error: {}",
                orderId,
                e.getMessage());
        }
    }
}
