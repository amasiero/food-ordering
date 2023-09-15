package me.amasiero.food.ordering.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.amasiero.food.ordering.domain.dto.create.CreateOrderCommand;
import me.amasiero.food.ordering.domain.dto.create.CreateOrderResponse;
import me.amasiero.food.ordering.domain.mapper.OrderDataMapper;
import me.amasiero.food.ordering.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import me.amasiero.food.ordering.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderCreatedCommandHandler {

    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent =  orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order created with id {}", orderCreatedEvent.order().getId().getValue());
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.order());
    }

}
