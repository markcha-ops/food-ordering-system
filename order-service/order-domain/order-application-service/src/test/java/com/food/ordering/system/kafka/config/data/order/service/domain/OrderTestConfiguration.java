package com.food.ordering.system.kafka.config.data.order.service.domain;

import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequsetMessagePublisher;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.repository.RestaurantRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderTestConfiguration {
    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }
    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }
    @Bean
    public OrderPaidRestaurantRequsetMessagePublisher orderPaidRestaurantRequsetMessagePublisher() {
        return Mockito.mock(OrderPaidRestaurantRequsetMessagePublisher.class);
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
