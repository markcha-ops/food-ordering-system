package com.food.ordering.system.kafka.config.data.order.service.domain.enriry;

import com.bood.ordering.system.domain.entity.AggregateRoot;
import com.bood.ordering.system.domain.valueobject.*;
import com.food.ordering.system.kafka.config.data.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.kafka.config.data.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.kafka.config.data.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.kafka.config.data.order.service.domain.valueobject.TrackingId;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;


    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;
    public static final String FAILURE_MESSAGE_DELIMITER = ",";

    public void initializeOrder() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItemns();
    }
    public void validateOrder() {
       validateInitialOrder();
       validateTotalPrice();
       validateItemsPrice();

    }
    public void pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not pending");
        }
        orderStatus = OrderStatus.PAID;
    }
    public void approve() {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not paid");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not paid");
        }
        orderStatus = OrderStatus.CANCELLING;
        updateFialureMessages(failureMessages);
    }

    private void updateFialureMessages(List<String> failureMessages) {
        if (this.failureMessages != null &&
        failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream()
                    .filter(message -> !message.isEmpty())
                    .toList());
        }
        if (this.failureMessages == null) {
            this.failureMessages = failureMessages.stream()
                    .filter(message -> !message.isEmpty())
                    .toList();
        }
    }

    public void cancel(List<String> failureMessages) {
        if (orderStatus != OrderStatus.CANCELLING || orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not cancelling or pending");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateFialureMessages(failureMessages);
    }
    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            throw new OrderDomainException("Order is already initialized");
        }
    }
    private void validateTotalPrice() {
        if (price == null || price.isGreaterThanZero()) {
            throw new OrderDomainException("Order total price is invalid");
        }
    }
    private void validateItemsPrice() {
        Money orderItemsTotal = items.stream()
                .map(orderItem -> {
                    validateItemPrice(orderItem);
                    return orderItem.getSubTotal();
                }).reduce(Money.ZERO, Money::add);
        if (!price.equals(orderItemsTotal)) {
            throw new OrderDomainException(
                    String.format("Total price: %s " +
                            " is not equal to Order items total: %s",
                            price, orderItemsTotal)
            );
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException(
                    String.format("Order item price: %s " +
                            " is not equal to product price: %s",
                            orderItem.getPrice(), orderItem.getProduct().getPrice())
            );
        }
    }

    private void initializeOrderItemns() {
        long itemId = 1L;
        for (OrderItem orderItem: items) {
            orderItem.initializeOrderItem(super.getId(),
                    new OrderItemId(itemId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.id);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }
    public static Builder builder() {
        return new Builder();
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private OrderId id;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder id(OrderId val) {
            id = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
