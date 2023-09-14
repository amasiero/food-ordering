package me.amasiero.food.ordering.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.amasiero.food.ordering.domain.dto.create.CreateOrderCommand;
import me.amasiero.food.ordering.domain.dto.create.CreateOrderResponse;
import me.amasiero.food.ordering.domain.dto.track.TrackOrderQuery;
import me.amasiero.food.ordering.domain.dto.track.TrackOrderResponse;
import me.amasiero.food.ordering.domain.ports.input.service.OrderApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderCreatedCommandHandler orderCreatedCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;
    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreatedCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
