package com.food.ordering.system.kafka.config.data.order.service.domain.ports.input.message.listener.payment;

import com.food.ordering.system.kafka.config.data.order.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCanceelled(PaymentResponse paymentResponse);
}
