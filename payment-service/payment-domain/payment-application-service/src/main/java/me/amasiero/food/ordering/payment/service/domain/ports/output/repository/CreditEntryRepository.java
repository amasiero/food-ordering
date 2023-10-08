package me.amasiero.food.ordering.payment.service.domain.ports.output.repository;

import java.util.Optional;

import me.amasiero.food.ordering.domain.valueobjects.CustomerId;
import me.amasiero.food.ordering.payment.service.domain.enttity.CreditEntry;

public interface CreditEntryRepository {
    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByCustomerId(CustomerId customerId);
}
