package me.amasiero.food.ordering.payment.service.domain.valueobject;

import java.util.UUID;

import me.amasiero.food.ordering.domain.valueobjects.BaseId;

public class PaymentId extends BaseId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
