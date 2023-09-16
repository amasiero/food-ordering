package me.amasiero.food.ordering.domain;

import me.amasiero.food.ordering.OrderDomainService;
import me.amasiero.food.ordering.OrderDomainServiceImpl;
import me.amasiero.food.ordering.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import me.amasiero.food.ordering.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import me.amasiero.food.ordering.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRequestMessagePublisher;
import me.amasiero.food.ordering.domain.ports.output.repository.CustomerRepository;
import me.amasiero.food.ordering.domain.ports.output.repository.OrderRepository;
import me.amasiero.food.ordering.domain.ports.output.repository.RestaurantRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "me.amasiero.food.ordering")
public class OrderTestConfig {

    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderPaidRequestMessagePublisher orderPaidRequestMessagePublisher() {
        return Mockito.mock(OrderPaidRequestMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
