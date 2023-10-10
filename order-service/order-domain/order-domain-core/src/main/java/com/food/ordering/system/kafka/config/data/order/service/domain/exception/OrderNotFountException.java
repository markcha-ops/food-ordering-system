package com.food.ordering.system.kafka.config.data.order.service.domain.exception;

public class OrderNotFountException extends OrderDomainException {
    public OrderNotFountException(String message) {
        super(message);
    }

    public OrderNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
