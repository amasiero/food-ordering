package me.amasiero.food.ordering.service.messaging.listener.kafka;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import me.amasiero.food.ordering.kafka.consumer.KafkaConsumer;
import me.amasiero.food.ordering.order.avro.model.PaymentResponseAvroModel;
import me.amasiero.food.ordering.order.avro.model.PaymentStatus;
import me.amasiero.food.ordering.service.messaging.mapper.OrderMessagingDataMapper;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseAvroModel> {

    private final PaymentResponseMessageListener paymentResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    @Override
    @KafkaListener(
        id = "${kafka-consumer-config.payment-consumer-group-id}",
        topics = "${order-service.payment-response-topic-name}"
    )
    public void receive(
        @Payload List<PaymentResponseAvroModel> messages,
        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
        @Header(KafkaHeaders.OFFSET) List<Long> offsets
    ) {
        log.info("{} number of payment response messages received from kafka with keys: {}, partitions: {} and offsets: {}",
            messages.size(),
            keys.toString(),
            partitions.toString(),
            offsets.toString()
        );

        messages.forEach(paymentResponseAvroModel -> {
            if (PaymentStatus.COMPLETED == paymentResponseAvroModel.getPaymentStatus()) {
                log.info("Processing successful payment for order id: {}", paymentResponseAvroModel.getOrderId());
                paymentResponseMessageListener.paymentCompleted(
                    orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel)
                );
            } else if (PaymentStatus.CANCELLED == paymentResponseAvroModel.getPaymentStatus() ||
                PaymentStatus.FAILED == paymentResponseAvroModel.getPaymentStatus()) {
                log.info("Processing unsuccessful payment for order id: {}", paymentResponseAvroModel.getOrderId());
                paymentResponseMessageListener.paymentCancelled(
                    orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel)
                );
            }
        });
    }
}
