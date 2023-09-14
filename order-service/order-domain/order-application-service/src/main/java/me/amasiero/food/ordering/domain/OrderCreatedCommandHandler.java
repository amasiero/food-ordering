package me.amasiero.food.ordering.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.amasiero.food.ordering.OrderDomainService;
import me.amasiero.food.ordering.domain.dto.create.CreateOrderCommand;
import me.amasiero.food.ordering.domain.dto.create.CreateOrderResponse;
import me.amasiero.food.ordering.domain.mapper.OrderDataMapper;
import me.amasiero.food.ordering.domain.ports.output.repository.CustomerRepository;
import me.amasiero.food.ordering.domain.ports.output.repository.OrderRepository;
import me.amasiero.food.ordering.domain.ports.output.repository.RestaurantRepository;
import me.amasiero.food.ordering.entity.Customer;
import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.entity.Restaurant;
import me.amasiero.food.ordering.event.OrderCreatedEvent;
import me.amasiero.food.ordering.exception.OrderDomainException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class OrderCreatedCommandHandler {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.customerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitializeOrder(order, restaurant);
        Order savedOrder = saveOrder(order);
        log.info("Order created with id {}", savedOrder.getId().getValue());
        return orderDataMapper.orderToCreateOrderResponse(savedOrder);
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByRestauratnInformation(restaurant);
        return restaurantOptional.orElseGet(() -> {
            log.warn("Could not find restaurant with id {}", createOrderCommand.restaurantId());
            throw new OrderDomainException("Could not find restaurant with id " + createOrderCommand.restaurantId());
        });
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with id {}", customerId);
            throw new OrderDomainException("Could not find customer with id " + customerId);
        }
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order");
            throw new OrderDomainException("Could not save order");
        }
        log.info("Order saved with id {}", orderResult.getId().getValue());
        return orderResult;
    }
}
