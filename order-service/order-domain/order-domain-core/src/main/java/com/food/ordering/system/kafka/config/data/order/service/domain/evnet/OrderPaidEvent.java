package com.food.ordering.system.kafka.config.data.order.service.domain.evnet;

import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent {
    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
