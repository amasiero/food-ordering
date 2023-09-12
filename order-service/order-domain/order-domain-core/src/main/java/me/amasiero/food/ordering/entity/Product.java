package me.amasiero.food.ordering.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.amasiero.food.ordering.domain.entity.BaseEntity;
import me.amasiero.food.ordering.domain.valueobjects.Money;
import me.amasiero.food.ordering.domain.valueobjects.ProductId;

@Getter
@AllArgsConstructor
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public void updateWithNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
