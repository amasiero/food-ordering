package me.amasiero.food.ordering.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.amasiero.food.ordering.domain.dto.track.TrackOrderQuery;
import me.amasiero.food.ordering.domain.dto.track.TrackOrderResponse;
import me.amasiero.food.ordering.domain.mapper.OrderDataMapper;
import me.amasiero.food.ordering.domain.ports.output.repository.OrderRepository;
import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.exception.OrderNotFoundException;
import me.amasiero.food.ordering.valueobjects.TrackingId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;


    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> order = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.trackingId()));

        return order.map(orderDataMapper::orderToTrackOrderResponse)
                .orElseGet(() -> {
                    log.warn("Order not found for trackingId {}", trackOrderQuery.trackingId());
                    throw new OrderNotFoundException("Order not found for trackingId " + trackOrderQuery.trackingId());
                });
    }
}
