package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.evnet.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMesseagepublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class OrderCreatedEventApplicationListener {
    private final OrderCreatedPaymentRequestMesseagepublisher orderCreatedPaymentRequestMesseagepublisher;

    public OrderCreatedEventApplicationListener(OrderCreatedPaymentRequestMesseagepublisher orderCreatedPaymentRequestMesseagepublisher) {
        this.orderCreatedPaymentRequestMesseagepublisher = orderCreatedPaymentRequestMesseagepublisher;
    }
    @Transactional
    public void process(OrderCreatedEvent orderCreatedEvent) {
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        orderCreatedPaymentRequestMesseagepublisher.publish(orderCreatedEvent);
    }
}
