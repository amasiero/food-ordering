package me.amasiero.food.ordering.domain.entity;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
public abstract class AggregateRoot<T> extends BaseEntity<T> {
}
