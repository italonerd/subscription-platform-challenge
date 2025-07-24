package com.example.subscription_platform_challenge.mapper;

import com.example.subscription_platform_challenge.dto.PaymentDTO;
import com.example.subscription_platform_challenge.exceptions.SubscriptionNotFoundException;
import com.example.subscription_platform_challenge.model.Payment;
import com.example.subscription_platform_challenge.model.Subscription;
import com.example.subscription_platform_challenge.model.enums.PaymentStatus;
import com.example.subscription_platform_challenge.model.enums.SubscriptionStatus;
import com.example.subscription_platform_challenge.repository.SubscriptionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentMapper {

    private final SubscriptionRepository subscriptionRepository;

    public PaymentMapper(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public PaymentDTO toDto(Payment payment) {

        return new PaymentDTO(
                payment.getId(),
                payment.getSubscription().getId(),
                payment.getAmount(),
                payment.getStatus().getStatus(),
                payment.getMethod(),
                payment.getPaymentDate(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }

    public Payment toPayment(PaymentDTO paymentDTO) {
        Optional.ofNullable(paymentDTO.getSubscriptionId())
                .orElseThrow(() -> new SubscriptionNotFoundException(paymentDTO.getSubscriptionId()));
        Subscription subscription = subscriptionRepository.findById(paymentDTO.getSubscriptionId())
                .orElseThrow(() -> new SubscriptionNotFoundException(paymentDTO.getSubscriptionId()));

        PaymentStatus paymentStatus = null;
        if (paymentDTO.getStatus() != null && !paymentDTO.getStatus().isEmpty()) {
            paymentStatus = PaymentStatus.valueOf(paymentDTO.getStatus());
        }

        return new Payment(
                paymentDTO.getId(),
                subscription,
                paymentDTO.getAmount(),
                paymentStatus,
                paymentDTO.getMethod(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getCreatedAt(),
                paymentDTO.getUpdatedAt()
        );
    }
}
