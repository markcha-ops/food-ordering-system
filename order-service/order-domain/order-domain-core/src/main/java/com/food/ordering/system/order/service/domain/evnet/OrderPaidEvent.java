package com.food.ordering.system.order.service.domain.evnet;

import com.bood.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.order.service.domain.enriry.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent {
    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
