package com.example.subscription_platform_challenge.model.enums;

public enum SubscriptionStatus {
    ACTIVE("ACTIVE"), CANCELLED("CANCELLED");

    private final String status;

    SubscriptionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
