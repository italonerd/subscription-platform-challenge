package com.example.subscription_platform_challenge.mapper;

import com.example.subscription_platform_challenge.dto.SubscriptionDTO;
import com.example.subscription_platform_challenge.model.Subscription;
import com.example.subscription_platform_challenge.model.enums.SubscriptionStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubscriptionMapper {
    public SubscriptionDTO toDto(Subscription subscription) {

        return new SubscriptionDTO(
                subscription.getId(),
                subscription.getClientId(),
                subscription.getPlan(),
                subscription.getStatus().getStatus(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getCreatedAt(),
                subscription.getUpdatedAt()
        );
    }

    public Subscription toSubscription(SubscriptionDTO subscriptionDTO) {
        SubscriptionStatus subscriptionStatus = null;
        if (subscriptionDTO.getStatus() != null && !subscriptionDTO.getStatus().isEmpty()) {
            subscriptionStatus = SubscriptionStatus.valueOf(subscriptionDTO.getStatus());
        }

        return new Subscription(
                subscriptionDTO.getId(),
                subscriptionDTO.getClientId(),
                subscriptionDTO.getPlan(),
                subscriptionStatus,
                subscriptionDTO.getStartDate(),
                subscriptionDTO.getEndDate(),
                subscriptionDTO.getCreatedAt(),
                subscriptionDTO.getUpdatedAt()
        );
    }
}
