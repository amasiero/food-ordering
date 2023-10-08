package me.amasiero.food.ordering.payment.service.domain.enttity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import me.amasiero.food.ordering.domain.entity.BaseEntity;
import me.amasiero.food.ordering.domain.valueobjects.CustomerId;
import me.amasiero.food.ordering.domain.valueobjects.Money;
import me.amasiero.food.ordering.payment.service.domain.valueobject.CreditHistoryId;
import me.amasiero.food.ordering.payment.service.domain.valueobject.TransactionType;

@Getter
@SuperBuilder
public class CreditHistory extends BaseEntity<CreditHistoryId> {
    private final CustomerId customerId;
    private final Money amount;
    private final TransactionType transactionType;
}
