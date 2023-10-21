package com.food.ordering.system.kafka.config.data.order.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "order-service")
public class OrderServiceConfigData {
    private String paymentRequsetTopicName;
    private String paymentResponseTopicName;
    private String restaurantRequestTopicName;
    private String restaurantResponseTopicName;
}
