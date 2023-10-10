package com.food.ordering.system.order.service.order.dataaccess.restaurant.mapper;

import com.bood.ordering.system.domain.valueobject.Money;
import com.bood.ordering.system.domain.valueobject.ProductId;
import com.bood.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Product;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Restaurant;
import com.food.ordering.system.order.service.order.dataaccess.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.order.service.order.dataaccess.restaurant.exception.RestauarantDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RestaurantDataAccessMapper {
    public List<UUID> restaurantToRestaurantProductIds(Restaurant restaurant) {
        return restaurant.getProducts().stream()
                .map(product -> product.getId().getValue())
                .toList();
    }
    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity =
                restaurantEntities.stream()
                        .findFirst().orElseThrow(() -> new RestauarantDataAccessException("Restaurant not found"));
        List<Product> restaurantProducts = restaurantEntities.stream()
                .map(t->new Product(new ProductId(t.getProductId()),
                        t.getProductName(),
                        new Money(t.getProductPrice())))
                .toList();
        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .products(restaurantProducts)
                .active(restaurantEntity.getRestaurantActive())
                .build();
    }
}
