package me.amasiero.food.ordering.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.amasiero.food.ordering.OrderDomainService;
import me.amasiero.food.ordering.OrderDomainServiceImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
