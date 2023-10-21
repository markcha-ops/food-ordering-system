package com.food.ordering.system.order.service.messaging.mapper;

import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Order;
import com.food.ordering.system.kafka.config.data.order.service.domain.evnet.OrderCancelledEvent;
import com.food.ordering.system.kafka.config.data.order.service.domain.evnet.OrderCreatedEvent;
import com.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus;
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import org.springframework.stereotype.Component;

@Component
public class OrderMessagingDataMapper {
    public PaymentRequestAvroModel orderCreatedEventToPaymentRequsetAvroModel(OrderCreatedEvent orderCreatedEvent) {
        Order order = orderCreatedEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(order.getId().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().toString())
                .setOrderId(order.getId().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();
    }
    public PaymentRequestAvroModel orderCencalledEventToPaymentRequsetAvroModel(OrderCancelledEvent orderCancelledEvent) {
        Order order = orderCancelledEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(order.getId().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().toString())
                .setOrderId(order.getId().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCancelledEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();
    }
}
