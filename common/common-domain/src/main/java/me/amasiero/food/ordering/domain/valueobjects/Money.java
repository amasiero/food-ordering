package me.amasiero.food.ordering.domain.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {

    public static final Money ZERO = new Money(BigDecimal.ZERO);
    public boolean isGreaterThanZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return amount != null && amount.compareTo(money.amount()) > 0;
    }

    public Money add(Money money) {
        return new Money(setScale(amount.add(money.amount())));
    }

    public Money subtract(Money money) {
        return new Money(setScale(amount.subtract(money.amount())));
    }

    public Money multiply(int times) {
        return new Money(setScale(amount.multiply(new BigDecimal(times))));
    }

    private BigDecimal setScale(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }
}
