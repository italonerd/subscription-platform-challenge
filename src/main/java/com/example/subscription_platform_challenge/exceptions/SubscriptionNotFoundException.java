package com.example.subscription_platform_challenge.exceptions;

public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException(Long id) {
        super("Could not find subscription " + id);
    }
}
