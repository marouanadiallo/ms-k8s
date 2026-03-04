package com.alphamar.customer;

import com.alphamar.amqp.NotificationExchangeConfigProps;
import com.alphamar.clients.notification.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

public interface NotificationPublisher {
    void publish(NotificationRequest notificationRequest);
}

@Component
class NotificationRabbitPublisherImpl implements NotificationPublisher {

    private final AmqpTemplate amqpTemplate;
    private final NotificationExchangeConfigProps notifExchangeConfig;
    private static final Logger logger = LoggerFactory.getLogger(NotificationRabbitPublisherImpl.class);

    public NotificationRabbitPublisherImpl(AmqpTemplate amqpTemplate, NotificationExchangeConfigProps notifExchangeConfig) {
        this.amqpTemplate = amqpTemplate;
        this.notifExchangeConfig = notifExchangeConfig;
    }

    @Override
    public void publish(NotificationRequest notificationRequest) {
        logger.info("Publishing message {} to exchange {} with routing key {}",
                notificationRequest,
                notifExchangeConfig.name(),
                notifExchangeConfig.routingKey());

        amqpTemplate.convertAndSend(
                notifExchangeConfig.name(),
                notifExchangeConfig.routingKey(),
                notificationRequest
        );
    }
}
