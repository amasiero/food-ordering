package me.amasiero.food.ordering.entity;

import lombok.experimental.SuperBuilder;
import me.amasiero.food.ordering.domain.entity.AggregateRoot;
import me.amasiero.food.ordering.domain.valueobjects.CustomerId;

@SuperBuilder
public class Customer extends AggregateRoot<CustomerId> {
}
