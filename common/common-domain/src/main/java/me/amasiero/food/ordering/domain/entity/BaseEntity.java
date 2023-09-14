package me.amasiero.food.ordering.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public abstract class BaseEntity<T> {
    private T id;
}
