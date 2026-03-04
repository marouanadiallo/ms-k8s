package com.alphamar.notification;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationService {

    void sendTo(String customerEmail, Long customerId, String message);
}

@Service
class NotificationServiceImpl implements NotificationService {
    private final JdbcClient jdbcClient;

    public NotificationServiceImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional
    @Override
    public void sendTo(String customerEmail, Long customerId, String message) {
        String sql = """
            INSERT INTO notifications (to_customer_id, to_customer_email, sender, message) VALUES (?, ?, ?, ?)""";
        this.jdbcClient.sql(sql)
                .param(customerId)
                .param(customerEmail)
                .param("System")
                .param(message)
                .update();
    }
}
