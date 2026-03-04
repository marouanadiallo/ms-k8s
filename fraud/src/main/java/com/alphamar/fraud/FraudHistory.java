package com.alphamar.fraud;

public record FraudHistory(Long id, Long customerId, String customerEmail, Boolean isFraudster) {
    public static FraudHistory of(Long customerId, String customerEmail, Boolean isFraudster) {
        return new FraudHistory(null, customerId, customerEmail, isFraudster);
    }
}
