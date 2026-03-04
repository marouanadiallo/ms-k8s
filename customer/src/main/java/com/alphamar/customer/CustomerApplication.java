package com.alphamar.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {
                "com.alphamar.customer",
                "com.alphamar.amqp"
        }
)
@EnableFeignClients(basePackages = {
        "com.alphamar.clients"
})
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active:default}.properties")
})
public class CustomerApplication {
    public static void main( String[] args ) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
