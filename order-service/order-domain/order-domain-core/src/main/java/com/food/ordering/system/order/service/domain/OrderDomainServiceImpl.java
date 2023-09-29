package com.food.ordering.system.order.service.domain;

import com.bood.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.order.service.domain.enriry.Order;
import com.food.ordering.system.order.service.domain.enriry.Product;
import com.food.ordering.system.order.service.domain.enriry.Restaurant;
import com.food.ordering.system.order.service.domain.evnet.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.evnet.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.evnet.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
    private static final String UTC = "UTC";
    @Override
    public OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInfomation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is created and ready for payment", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    private void setOrderProductInfomation(Order order, Restaurant restaurant) {
        Map<ProductId, Product> productMap = restaurant.getProducts().stream()
                .collect(toMap(Product::getId, product -> product));
        order.getItems().forEach(orderItem -> {
            Product currentProduct = orderItem.getProduct();
            Product restaurantProduct = productMap.get(currentProduct.getId());
            if (currentProduct.equals(restaurantProduct)) {
                currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(),
                        restaurantProduct.getPrice());
            }
        });
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException(String.format("Restaurant with id: %s is not active", restaurant.getId().getValue()));
        }
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} is approved", order.getId().getValue());

    }

    @Override
    public OrderCancelledEvent cancalOrderPayment(Order order, List<String> failedMessages) {
        order.initCancel(failedMessages);
        log.info("Order payment is cancelling for order id : {}", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failedMessages) {
        order.cancel(failedMessages);
        log.info("Order with id: {} is cancelled", order.getId().getValue());
    }
}
