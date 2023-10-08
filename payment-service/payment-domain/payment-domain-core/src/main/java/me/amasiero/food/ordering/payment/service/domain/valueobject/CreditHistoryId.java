package me.amasiero.food.ordering.payment.service.domain.valueobject;

import java.util.UUID;

import me.amasiero.food.ordering.domain.valueobjects.BaseId;

public class CreditHistoryId extends BaseId<UUID> {
    public CreditHistoryId(UUID value) {
        super(value);
    }
}
