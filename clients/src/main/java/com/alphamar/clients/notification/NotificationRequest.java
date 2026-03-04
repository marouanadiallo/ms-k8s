package com.alphamar.notification;

public record NotificationRequest (
    Long toCustomerId,
    String toCustomerEmail,
    String message
) { }
