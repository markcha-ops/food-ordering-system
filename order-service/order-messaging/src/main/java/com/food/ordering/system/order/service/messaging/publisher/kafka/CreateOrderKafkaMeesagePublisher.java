package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.config.data.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.kafka.config.data.order.service.domain.evnet.OrderCreatedEvent;
import com.food.ordering.system.kafka.config.data.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class CreateOrderKafkaMeesagePublisher implements OrderCreatedPaymentRequestMessagePublisher{
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;

    public CreateOrderKafkaMeesagePublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                            OrderServiceConfigData orderServiceConfigData,
                                            KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void publish(OrderCreatedEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().toString();
        log.info("Received order created event for order id: {}", orderId);

        PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper
                .orderCreatedEventToPaymentRequsetAvroModel(domainEvent);
        kafkaProducer.send(orderServiceConfigData.getPaymentRequsetTopicName(),
                orderId, paymentRequestAvroModel,
                getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(), paymentRequestAvroModel));
    }

    private ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>>
    getKafkaCallback(String paymentResponseTopicName, PaymentRequestAvroModel paymentRequestAvroModel) {
        return new ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Failed to publish payment request message for order id: {}",
                        paymentRequestAvroModel.getOrderId(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, PaymentRequestAvroModel> result) {

            }
        };
    }
}
