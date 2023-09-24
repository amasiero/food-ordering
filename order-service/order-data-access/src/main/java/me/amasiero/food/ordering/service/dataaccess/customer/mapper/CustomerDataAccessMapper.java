package me.amasiero.food.ordering.service.dataaccess.customer.mapper;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.valueobjects.CustomerId;
import me.amasiero.food.ordering.entity.Customer;
import me.amasiero.food.ordering.service.dataaccess.customer.entity.CustomerEntity;

@Component
public class CustomerDataAccessMapper {

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return Customer.builder()
                       .id(new CustomerId(customerEntity.getId()))
                       .build();
    }
}
