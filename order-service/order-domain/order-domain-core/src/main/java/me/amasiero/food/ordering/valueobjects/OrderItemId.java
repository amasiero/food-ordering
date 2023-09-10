package me.amasiero.food.ordering.valueobjects;

import me.amasiero.food.ordering.domain.valueobjects.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
