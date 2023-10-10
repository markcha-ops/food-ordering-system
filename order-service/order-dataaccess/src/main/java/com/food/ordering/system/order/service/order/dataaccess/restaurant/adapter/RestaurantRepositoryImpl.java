package com.food.ordering.system.order.service.order.dataaccess.restaurant.adapter;

import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Restaurant;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.repository.RestaurantRepository;
import com.food.ordering.system.order.service.order.dataaccess.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.order.service.order.dataaccess.restaurant.mapper.RestaurantDataAccessMapper;
import com.food.ordering.system.order.service.order.dataaccess.restaurant.repository.RestaurantJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    public RestaurantRepositoryImpl(RestaurantJpaRepository restaurantJpaRepository, RestaurantDataAccessMapper restaurantDataAccessMapper) {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.restaurantDataAccessMapper = restaurantDataAccessMapper;
    }

    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant repository) {
        List<UUID> restaurantProductIds = restaurantDataAccessMapper.restaurantToRestaurantProductIds(repository);
        Optional<List<RestaurantEntity>> restaurantEntities = restaurantJpaRepository.findByRestaurantIdAndProductIdIn(repository.getId().getValue(), restaurantProductIds);
        return restaurantEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
