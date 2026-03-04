package com.alphamar.eurekaserver.customer;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.Objects;

public interface CustomerService {
    Long save(Customer customer);
}

@Service
class CustomerServiceImpl implements CustomerService {
    private final JdbcClient jdbcClient;


    public CustomerServiceImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional
    @Override
    public Long save(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("INSERT INTO customers (name, email) VALUES (?, ?)")
                .param(customer.name())
                .param(customer.email())
                .update(keyHolder, "id");

        Number newId = keyHolder.getKey();
        if (Objects.isNull(newId)) {
            throw new RuntimeException("Failed to retrieve generated ID");
        }
        return newId.longValue();
    }

}