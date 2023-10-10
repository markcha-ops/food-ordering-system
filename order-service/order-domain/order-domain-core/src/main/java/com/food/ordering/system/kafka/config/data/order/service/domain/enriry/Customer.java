package com.food.ordering.system.kafka.config.data.order.service.domain.enriry;

import com.bood.ordering.system.domain.entity.AggregateRoot;
import com.bood.ordering.system.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    private String name;
    private String phone;

    public Customer(CustomerId value, String name, String phone) {
        super.setId(value);
        this.name = name;
        this.phone = phone;
    }
    public Customer(CustomerId id) {
        super.setId(id);
    }
    public Customer() {

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
