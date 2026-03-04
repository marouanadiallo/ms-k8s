package com.alphamar.notification;

import com.alphamar.amqp.NotificationExchangeConfigProps;
import com.alphamar.clients.notification.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConsumer {
    private final ConnectionFactory connectionFactory;
    private final NotificationService notificationService;
    private final MessageConverter messageConverter;
    private final NotificationExchangeConfigProps notifExchangeConfig;
    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    public NotificationConsumer(ConnectionFactory connectionFactory,
                                NotificationService notificationService,
                                MessageConverter messageConverter,
                                NotificationExchangeConfigProps notifExchangeConfig) {
        this.connectionFactory = connectionFactory;
        this.notificationService = notificationService;
        this.messageConverter = messageConverter;
        this.notifExchangeConfig = notifExchangeConfig;
    }

    @Bean
    public SimpleMessageListenerContainer getSimpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(notifExchangeConfig.queueName());
        container.setMessageListener(messageListener());
        return container;
    }

    @Bean
    public MessageListener messageListener() {
        return message -> {
            var msg = (NotificationRequest) messageConverter.fromMessage(message);
            logger.info("Received message from queue {}: {}", notifExchangeConfig.queueName(), msg);
            this.notificationService.sendTo(
                    msg.toCustomerEmail(),
                    msg.toCustomerId(),
                    msg.message()
            );
        };
    }
}
