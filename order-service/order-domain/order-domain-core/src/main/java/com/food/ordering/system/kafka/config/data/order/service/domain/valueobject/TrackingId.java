package com.food.ordering.system.kafka.config.data.order.service.domain.valueobject;

import com.bood.ordering.system.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
