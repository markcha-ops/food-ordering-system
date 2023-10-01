package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.enriry.Order;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.exception.OrderNotFountException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class OrderTrackCammandHandler {
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderTrackCammandHandler(OrderRepository orderRepository,
                                    OrderDataMapper orderDataMapper) {
        this.orderRepository = orderRepository;
        this.orderDataMapper = orderDataMapper;
    }
    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (optionalOrder.isEmpty()) {
            log.warn("Order with tracking id: {} is not found", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFountException("Order is not found id : " + trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(optionalOrder.get());
    }
}
