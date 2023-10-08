package me.amasiero.food.ordering.payment.service.domain.valueobject;

import java.util.UUID;

import me.amasiero.food.ordering.domain.valueobjects.BaseId;

public class CreditEntryId extends BaseId<UUID> {
    public CreditEntryId(UUID value) {
        super(value);
    }
}
