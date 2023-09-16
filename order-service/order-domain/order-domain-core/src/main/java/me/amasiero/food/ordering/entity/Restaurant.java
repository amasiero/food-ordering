package me.amasiero.food.ordering.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import me.amasiero.food.ordering.domain.entity.AggregateRoot;
import me.amasiero.food.ordering.domain.valueobjects.RestaurantId;

import java.util.List;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Restaurant extends AggregateRoot<RestaurantId> {
    private final List<Product> products;
    private final boolean active;
}
