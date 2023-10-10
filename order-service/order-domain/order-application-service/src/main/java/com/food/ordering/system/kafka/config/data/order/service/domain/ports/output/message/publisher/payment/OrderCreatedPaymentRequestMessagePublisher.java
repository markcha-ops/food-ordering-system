package com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.message.publisher.payment;

import com.bood.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.kafka.config.data.order.service.domain.evnet.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
