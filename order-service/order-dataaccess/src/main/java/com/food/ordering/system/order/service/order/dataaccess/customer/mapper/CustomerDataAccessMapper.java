package com.food.ordering.system.order.service.order.dataaccess.customer.mapper;

import com.bood.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Customer;
import com.food.ordering.system.order.service.order.dataaccess.customer.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {
    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
