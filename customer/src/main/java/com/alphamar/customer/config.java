package com.alphamar.customer;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@Configuration
@Profile("eureka")
public class config {

    @LoadBalanced
    @Bean
    RestTemplateBuilder loadBalancedRestTemplateBuilder() {
        return new RestTemplateBuilder();
    }
}
