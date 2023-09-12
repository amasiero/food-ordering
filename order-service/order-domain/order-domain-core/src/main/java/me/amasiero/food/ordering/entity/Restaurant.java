package me.amasiero.food.ordering.entity;

import lombok.Builder;
import lombok.Getter;
import me.amasiero.food.ordering.domain.entity.AggregateRoot;
import me.amasiero.food.ordering.domain.valueobjects.RestaurantId;

import java.util.List;

@Getter
@Builder
public class Restaurant extends AggregateRoot<RestaurantId> {
    private final List<Product> products;
    private final boolean active;
}
