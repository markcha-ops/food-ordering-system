package com.food.ordering.system.kafka.config.data.order.service.domain.evnet;

import com.bood.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Order;

import java.time.ZonedDateTime;

public class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;

    public OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
