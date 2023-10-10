package com.food.ordering.system.order.service.order.dataaccess.customer.adapter;

import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Customer;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.order.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.food.ordering.system.order.service.order.dataaccess.customer.repository.CustomerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;

    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository,
                                  CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId)
                .map(customerEntity -> customerDataAccessMapper.customerEntityToCustomer(customerEntity));
    }
}
