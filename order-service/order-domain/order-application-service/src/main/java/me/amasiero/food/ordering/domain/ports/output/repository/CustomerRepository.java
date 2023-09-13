package me.amasiero.food.ordering.domain.ports.output.repository;

import me.amasiero.food.ordering.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);
}
