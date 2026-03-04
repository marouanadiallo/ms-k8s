package com.alphamar.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({NotificationExchangeConfigProps.class})
public class NotificationExchangeConfiguration {

    private final NotificationExchangeConfigProps configProps;

    public NotificationExchangeConfiguration(NotificationExchangeConfigProps configProps) {
        this.configProps = configProps;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(configProps.name());
    }

    @Bean
    public Queue queue() {
        return new Queue(configProps.queueName());
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(topicExchange())
                .with(configProps.routingKey());
    }
}
