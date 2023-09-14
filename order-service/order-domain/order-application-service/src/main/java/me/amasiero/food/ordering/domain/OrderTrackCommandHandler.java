package me.amasiero.food.ordering.domain;

import lombok.extern.slf4j.Slf4j;
import me.amasiero.food.ordering.domain.dto.track.TrackOrderQuery;
import me.amasiero.food.ordering.domain.dto.track.TrackOrderResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderTrackCommandHandler {
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
