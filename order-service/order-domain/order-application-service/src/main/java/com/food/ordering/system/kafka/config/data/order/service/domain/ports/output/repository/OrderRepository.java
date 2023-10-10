package com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.repository;

import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Order;
import com.food.ordering.system.kafka.config.data.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);


}
