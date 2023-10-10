package com.food.ordering.system.kafka.config.data.order.service.domain;

import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Order;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Restaurant;
import com.food.ordering.system.kafka.config.data.order.service.domain.evnet.OrderCancelledEvent;
import com.food.ordering.system.kafka.config.data.order.service.domain.evnet.OrderCreatedEvent;
import com.food.ordering.system.kafka.config.data.order.service.domain.evnet.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCancelledEvent cancalOrderPayment(Order order, List<String> failedMessages);
    void cancelOrder(Order order, List<String> failedMessages);


}
