package me.amasiero.food.ordering.service.dataaccess.customer.adapter;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.ports.output.repository.CustomerRepository;
import me.amasiero.food.ordering.entity.Customer;
import me.amasiero.food.ordering.service.dataaccess.customer.mapper.CustomerDataAccessMapper;
import me.amasiero.food.ordering.service.dataaccess.customer.repository.CustomerJpaRepository;

@Component
@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId)
                                    .map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
