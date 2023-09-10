package me.amasiero.food.ordering.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.amasiero.food.ordering.domain.entity.BaseEntity;
import me.amasiero.food.ordering.domain.valueobjects.Money;
import me.amasiero.food.ordering.domain.valueobjects.OrderId;
import me.amasiero.food.ordering.valueobjects.OrderItemId;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItem extends BaseEntity<OrderItemId> {
    @Setter
    private OrderId orderId;
    private final Product product;
    private final Integer quantity;
    private final Money price;
    private final Money subTotal;

    protected void initializerOrderItem(OrderId orderId, OrderItemId itemId) {
        setId(new OrderItemId(itemId.getValue()));
        this.orderId = orderId;
    }

    protected boolean isPriceValid() {
        return price.equals(product.getPrice()) &&
                price.isGreaterThanZero() &&
                price.multiply(quantity).equals(subTotal);
    }

    public Money getSubTotal() {
        return price.multiply(quantity);
    }
}
