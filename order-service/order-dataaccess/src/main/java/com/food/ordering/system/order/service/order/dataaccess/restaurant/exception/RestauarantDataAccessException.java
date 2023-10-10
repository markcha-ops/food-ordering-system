package com.food.ordering.system.order.service.order.dataaccess.restaurant.exception;

public class RestauarantDataAccessException extends RuntimeException {
    public RestauarantDataAccessException(String restaurantNotFound) {
        super(restaurantNotFound);
    }
}
