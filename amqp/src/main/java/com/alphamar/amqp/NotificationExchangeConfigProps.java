package com.alphamar.amqp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "notification.exchange")
public record NotificationExchangeConfigProps(String name, String queueName, String routingKey) { }
