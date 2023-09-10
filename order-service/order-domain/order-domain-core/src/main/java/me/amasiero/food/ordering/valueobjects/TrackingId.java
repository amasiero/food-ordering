package me.amasiero.food.ordering.valueobjects;

import me.amasiero.food.ordering.domain.valueobjects.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}

