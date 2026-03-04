package com.alphamar.customer;

public record Customer(String id, String name, String email) {
    public static Customer of(String name, String email) {
        return new Customer(null, name, email);
    }
}
