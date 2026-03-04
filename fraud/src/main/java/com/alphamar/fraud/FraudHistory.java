package com.alphamar.eurekaserver.fraud;

public record FraudHistory(Long id, Long customerId, Boolean isFraudster) {
    public static FraudHistory of(Long customerId, Boolean isFraudster) {
        return new FraudHistory(null, customerId, isFraudster);
    }
}
