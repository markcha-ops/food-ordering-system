package com.food.ordering.system.kafka.config.data.order.service.domain;

import com.bood.ordering.system.domain.valueobject.*;
import com.food.ordering.system.kafka.config.data.order.service.domain.dto.create.OrderItem;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Customer;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Order;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Product;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Restaurant;
import com.food.ordering.system.kafka.config.data.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.kafka.config.data.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.kafka.config.data.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.kafka.config.data.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.input.service.OrderApplicationService;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {
    @Autowired
    @Qualifier("orderApplicationServiceImpl")
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
    private CreateOrderCommand createOrderCommandWorngPirce;
    private CreateOrderCommand createOrderCommandWorngProductPrice;
    private final UUID CUSTOMER_ID = UUID.fromString("f6d7e2c2-8e7a-4b0f-9b4a-6a6e6b0f9f9f");
    private final UUID RESTAURANT_ID = UUID.fromString("f6d7e2c2-8e7a-4b0f-9b4a-6a6e6b0f9f9f");
    private final UUID PRODUCT_ID = UUID.fromString("f6d7e2c2-8e7a-4b0f-9b4a-6a6e6b0f9f9f");
    private final UUID ORDER_ID = UUID.fromString("f6d7e2c2-8e7a-4b0f-9b4a-6a6e6b0f9f9f");
    private final BigDecimal PRICE = new BigDecimal("200.00");
    @BeforeAll
    public void init() {
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("10000AB")
                        .city("Paris")
                        .build())
                .price(PRICE)
                .items(List.of(
                       OrderItem.builder()
                               .productId(PRODUCT_ID)
                               .quantity(1)
                               .price(new BigDecimal("50.00"))
                               .subTotal(new BigDecimal("50.00"))
                               .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        createOrderCommandWorngPirce = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("10000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        createOrderCommandWorngProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("10000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurant = Restaurant.builder()
                .restaurantId(new RestaurantId(RESTAURANT_ID))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID), "product_1",
                                new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product_2",
                                new Money(new BigDecimal("50.00")))
                ))
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));
        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(
                orderDataMapper.createOrderCommandToRestaurant(createOrderCommand))).thenReturn(Optional.of(restaurant));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }
    @Test
    public void testCreateOrder() {
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(createOrderResponse.getOrderStatus(), OrderStatus.PENDING);
        assertEquals(createOrderResponse.getMessage(), "Order create successfully");
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }

}
