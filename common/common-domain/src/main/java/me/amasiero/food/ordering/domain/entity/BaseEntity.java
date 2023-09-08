package me.amasiero.food.ordering.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(of = "id")
public abstract class BaseEntity<T> {
    private T id;
}
