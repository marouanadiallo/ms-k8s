package com.alphamar.notification;

import java.time.LocalDateTime;

public record Notification (
        Long id,
        Long toCustomerId,
        String toCustomerEmail,
        String sender,
        String message,
        LocalDateTime sentAt
) {
}
