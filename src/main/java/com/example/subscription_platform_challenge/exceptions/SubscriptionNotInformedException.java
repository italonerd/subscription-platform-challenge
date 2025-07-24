package com.example.subscription_platform_challenge.exceptions;

public class SubscriptionNotInformedException extends RuntimeException {

    public SubscriptionNotInformedException() {
        super("subscriptionId must be informed");
    }
}
