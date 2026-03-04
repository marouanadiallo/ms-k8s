package com.alphamar.fraud;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

public interface FraudulentService {
    void defineFraudHistory(FraudHistory fraudHistory);
}

@Service
class FraudulentServiceImpl implements FraudulentService {
    private final JdbcClient jdbcClient;

    public FraudulentServiceImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void defineFraudHistory(FraudHistory fraudHistory) {
        var sql = "INSERT INTO fraud_history (customer_id, customer_email, is_fraudster) VALUES (?, ?, ?)";
        jdbcClient.sql(sql)
                .param(fraudHistory.customerId())
                .param(fraudHistory.customerEmail())
                .param(fraudHistory.isFraudster())
                .update();
    }
}
