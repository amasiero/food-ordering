package me.amasiero.food.ordering.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class BaseId<T> {
    private final T value;
}
