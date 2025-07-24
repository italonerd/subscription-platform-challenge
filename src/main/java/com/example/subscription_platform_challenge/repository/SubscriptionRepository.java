package com.example.subscription_platform_challenge.repository;

import com.example.subscription_platform_challenge.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
