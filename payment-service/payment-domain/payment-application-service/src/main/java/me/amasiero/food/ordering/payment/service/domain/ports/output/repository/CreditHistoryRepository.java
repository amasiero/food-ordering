package me.amasiero.food.ordering.payment.service.domain.ports.output.repository;

import java.util.List;
import java.util.Optional;

import me.amasiero.food.ordering.domain.valueobjects.CustomerId;
import me.amasiero.food.ordering.payment.service.domain.enttity.CreditHistory;

public interface CreditHistoryRepository {
    CreditHistory save(CreditHistory creditHistory);

    Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId);
}
