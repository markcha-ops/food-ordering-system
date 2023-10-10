package com.food.ordering.system.kafka.config.data.order.service.domain.ports.input.service;

import com.food.ordering.system.kafka.config.data.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.kafka.config.data.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.kafka.config.data.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.kafka.config.data.order.service.domain.dto.create.CreateOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand command);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
