package com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.repository;


import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Restaurant;


import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant repository);

}
