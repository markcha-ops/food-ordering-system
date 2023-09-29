package com.food.ordering.system.order.service.domain.ports.output.repository;


import com.food.ordering.system.order.service.domain.enriry.Restaurant;
import org.springframework.stereotype.Repository;


import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant repository);

}
