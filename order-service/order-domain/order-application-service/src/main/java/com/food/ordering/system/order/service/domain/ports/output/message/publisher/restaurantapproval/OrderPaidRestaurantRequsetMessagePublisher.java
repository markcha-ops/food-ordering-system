package com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval;

import com.bood.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.evnet.OrderPaidEvent;

public interface OrderPaidRestaurantRequsetMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
