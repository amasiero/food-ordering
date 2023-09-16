package me.amasiero.food.ordering.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import me.amasiero.food.ordering.domain.entity.BaseEntity;
import me.amasiero.food.ordering.domain.valueobjects.Money;
import me.amasiero.food.ordering.domain.valueobjects.ProductId;

@Getter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public void updateWithNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
