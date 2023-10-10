package com.food.ordering.system.order.service.order.dataaccess.order.mapper;

import com.bood.ordering.system.domain.valueobject.*;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Order;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.OrderItem;
import com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Product;
import com.food.ordering.system.kafka.config.data.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.kafka.config.data.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.kafka.config.data.order.service.domain.valueobject.TrackingId;
import com.food.ordering.system.order.service.order.dataaccess.order.entity.OrderAddressEntity;
import com.food.ordering.system.order.service.order.dataaccess.order.entity.OrderEntity;
import com.food.ordering.system.order.service.order.dataaccess.order.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.food.ordering.system.kafka.config.data.order.service.domain.enriry.Order.FAILURE_MESSAGE_DELIMITER;

@Component
public class OrderDataAccessMapper {
    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .customerId(order.getCustomerId().getValue())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .price(order.getPrice().getAmount())
                .itmes(orderItemsToOrderItemEntities(order.getItems()))
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages() != null ? String.join(FAILURE_MESSAGE_DELIMITER,
                        order.getFailureMessages()) : "")
                .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItmes().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }
    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .id(new OrderId(orderEntity.getId()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                .price(new Money(orderEntity.getPrice()))
                .items(orderItemEntitiesToOrderItems(orderEntity.getItmes()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessages(orderEntity.getFailureMessages().isBlank()? new ArrayList<>():
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages().split(FAILURE_MESSAGE_DELIMITER))))
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> itmes) {
        return itmes.stream()
                .map(orderItemEntity -> {
                    return OrderItem.builder()
                            .orderId(new OrderItemId(orderItemEntity.getId()))
                            .price(new Money(orderItemEntity.getPrice()))
                            .subTotal(new Money(orderItemEntity.getSubTotal()))
                            .quantity(orderItemEntity.getQuantity())
                            .product(new Product(new ProductId(orderItemEntity.getProductId())))
                            .build();
                }).toList();
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(address.getId(), address.getStreet(), address.getPostalCode(), address.getCity());
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream()
                .map(orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .productId(orderItem.getProduct().getId().getValue())
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal().getAmount())
                        .build()).collect(java.util.stream.Collectors.toList());
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        return OrderAddressEntity.builder()
                .id(deliveryAddress.getId())
                .street(deliveryAddress.getStreet())
                .city(deliveryAddress.getCity())
                .postalCode(deliveryAddress.getPostalCode())
                .build();
    }
}
