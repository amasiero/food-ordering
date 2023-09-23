package me.amasiero.food.ordering.service.dataaccess.order.adapter;

import java.util.Optional;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.ports.output.repository.OrderRepository;
import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.service.dataaccess.order.entity.OrderEntity;
import me.amasiero.food.ordering.service.dataaccess.order.mapper.OrderDataAccessMapper;
import me.amasiero.food.ordering.service.dataaccess.order.repository.OrderJpaRepository;
import me.amasiero.food.ordering.valueobjects.TrackingId;

@Component
@AllArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order));
        return orderDataAccessMapper.orderEntityToOrder(orderEntity);
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue())
                                 .map(orderDataAccessMapper::orderEntityToOrder);
    }
}
