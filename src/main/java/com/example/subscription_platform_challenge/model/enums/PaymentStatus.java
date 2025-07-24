package com.example.subscription_platform_challenge.model.enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum PaymentStatus {
    APPROVED("APPROVED"), PENDING("PENDING"), REJECTED("REJECTED");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    private static final Random random = new Random();

    public static PaymentStatus randomValue() {
        PaymentStatus[] paymentStatuses = {PaymentStatus.APPROVED, PaymentStatus.REJECTED};
        return paymentStatuses[random.nextInt(paymentStatuses.length)];
    }

}
