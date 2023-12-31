package com.food.ordering.system.kafka.config.data.order.service.domain;

import com.food.ordering.system.kafka.config.data.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.kafka.config.data.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.kafka.config.data.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.kafka.config.data.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service("orderApplicationServiceImpl")
public class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCammandHandler orderTrackCommandHandler;
    public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, OrderTrackCammandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }
    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }
    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
