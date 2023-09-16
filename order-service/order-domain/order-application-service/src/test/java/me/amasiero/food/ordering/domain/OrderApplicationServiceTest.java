package me.amasiero.food.ordering.domain;

import me.amasiero.food.ordering.domain.dto.create.CreateOrderCommand;
import me.amasiero.food.ordering.domain.dto.create.OrderAddress;
import me.amasiero.food.ordering.domain.dto.create.OrderItem;
import me.amasiero.food.ordering.domain.mapper.OrderDataMapper;
import me.amasiero.food.ordering.domain.ports.input.service.OrderApplicationService;
import me.amasiero.food.ordering.domain.ports.output.repository.CustomerRepository;
import me.amasiero.food.ordering.domain.ports.output.repository.OrderRepository;
import me.amasiero.food.ordering.domain.ports.output.repository.RestaurantRepository;
import me.amasiero.food.ordering.domain.valueobjects.CustomerId;
import me.amasiero.food.ordering.domain.valueobjects.Money;
import me.amasiero.food.ordering.domain.valueobjects.OrderId;
import me.amasiero.food.ordering.domain.valueobjects.OrderStatus;
import me.amasiero.food.ordering.domain.valueobjects.ProductId;
import me.amasiero.food.ordering.domain.valueobjects.RestaurantId;
import me.amasiero.food.ordering.entity.Customer;
import me.amasiero.food.ordering.entity.Order;
import me.amasiero.food.ordering.entity.Product;
import me.amasiero.food.ordering.entity.Restaurant;
import me.amasiero.food.ordering.exception.OrderDomainException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfig.class)
class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWithWrongPrice;
    private CreateOrderCommand createOrderCommandWithWrongProductPrice;
    private final UUID CUSTOMER_ID = UUID.fromString("aa2a3d7c-45dc-49e3-9eb0-eb2896c9774f");
    private final UUID RESTAURANT_ID = UUID.fromString("aa2a3d7c-45dc-49e3-9eb0-eb2896c9774f");
    private final UUID PRODUCT_ID = UUID.fromString("aa2a3d7c-45dc-49e3-9eb0-eb2896c9774f");
    private final UUID ORDER_ID = UUID.fromString("c1b51668-1a21-4af3-87a5-5a7597ac8b1d");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeEach
    void init() {
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .city("Lisbon")
                        .postalCode("1800-000")
                        .build())
                .price(PRICE)
                .items(List.of(
                        OrderItem.builder()
                            .productId(PRODUCT_ID)
                            .quantity(1)
                            .price(new Money(new BigDecimal("50.00")))
                            .subTotal(new Money(new BigDecimal("50.00")))
                            .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build())
                )
                .build();

        createOrderCommandWithWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .city("Lisbon")
                        .postalCode("1800-000")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("50.00")))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build())
                )
                .build();

        createOrderCommandWithWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .city("Lisbon")
                        .postalCode("1800-000")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new Money(new BigDecimal("60.00")))
                                .subTotal(new Money(new BigDecimal("60.00")))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build())
                )
                .build();

        var customer = Customer.builder()
                .id(new CustomerId(CUSTOMER_ID))
                .build();

        var restaurantResponse = Restaurant.builder()
                .id(new RestaurantId(createOrderCommand.restaurantId()))
                .products(List.of(
                        Product.builder()
                                .id(new ProductId(PRODUCT_ID))
                                .name("product-1")
                                .price(new Money(new BigDecimal("50.00")))
                                .build(),
                        Product.builder()
                                .id(new ProductId(PRODUCT_ID))
                                .name("product-2")
                                .price(new Money(new BigDecimal("50.00")))
                                .build()
                ))
                .active(true)
                .build();

        var order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findByRestauratnInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    void createOrder() {
        var response = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, response.status());
        assertEquals("Order created successfully", response.message());
        assertNotNull(response.trackingId());
    }

    @Test
    void createOrderCommandWithWrongPrice() {
        var exception = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWithWrongPrice));
        assertEquals("Order total price does not match the sum of order items prices.", exception.getMessage());
    }

    @Test
    void createOrderCommandWithWrongProductPrice() {
        var exception = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWithWrongProductPrice));
        assertEquals("Order item price is not valid.", exception.getMessage());
    }

    @Test
    void createOrderCommandWithPassiveRestaurant() {
        var restaurantResponse = Restaurant.builder()
                .id(new RestaurantId(createOrderCommand.restaurantId()))
                .products(List.of(
                        Product.builder()
                                .id(new ProductId(PRODUCT_ID))
                                .name("product-1")
                                .price(new Money(new BigDecimal("50.00")))
                                .build(),
                        Product.builder()
                                .id(new ProductId(PRODUCT_ID))
                                .name("product-2")
                                .price(new Money(new BigDecimal("50.00")))
                                .build()
                ))
                .active(false)
                .build();

        when(restaurantRepository.findByRestauratnInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        var orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommand));
        assertEquals(String.format("Restaurant with id: [%s] is not active.", RESTAURANT_ID), orderDomainException.getMessage());
    }
}