package com.alphamar.eurekaserver.fraud;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

public interface FraudCheckService {
    Boolean isFraudster(Long customerId);
}

@Service
class FraudCheckServiceImpl implements FraudCheckService {
    private final JdbcClient jdbcClient;

    public FraudCheckServiceImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Boolean isFraudster(Long customerId) {
        var sql = "SELECT is_fraudster FROM fraud_history WHERE customer_id = ?";
        return jdbcClient.sql(sql)
                .param(customerId)
                .query(
                        (rs) -> rs.next() && rs.getBoolean("is_fraudster")
                );
    }
}
