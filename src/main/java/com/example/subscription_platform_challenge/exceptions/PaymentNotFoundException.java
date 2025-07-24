package com.example.subscription_platform_challenge.exceptions;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(Long id) {
        super("Could not find payment " + id);
    }
}
