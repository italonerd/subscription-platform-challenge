package com.example.subscription_platform_challenge.service;

import com.example.subscription_platform_challenge.exceptions.SubscriptionNotFoundException;
import com.example.subscription_platform_challenge.model.Subscription;
import com.example.subscription_platform_challenge.model.enums.SubscriptionStatus;
import com.example.subscription_platform_challenge.repository.SubscriptionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

    public Optional<Subscription> getOne(Long id) {
        return subscriptionRepository.findById(id);
    }

    public Subscription register(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription update(Long id, Subscription updatedSubscription) {
        return subscriptionRepository
                .findById(id)
                .map(sub -> {
                    sub.setClientId(Optional.ofNullable(updatedSubscription.getClientId()).orElse(sub.getClientId()));
                    sub.setPlan(Optional.ofNullable(updatedSubscription.getPlan()).orElse(sub.getPlan()));
                    sub.setStatus(Optional.ofNullable(updatedSubscription.getStatus()).orElse(sub.getStatus()));
                    sub.setStartDate(Optional.ofNullable(updatedSubscription.getStartDate()).orElse(sub.getStartDate()));
                    sub.setEndDate(Optional.ofNullable(updatedSubscription.getEndDate()).orElse(sub.getEndDate()));

                    return subscriptionRepository.save(sub);
                }).orElseGet(() -> subscriptionRepository.save(updatedSubscription));
    }

    public void cancel(Long id) {
        subscriptionRepository.findById(id)
                .map(subscription -> {
                    subscription.setStatus(SubscriptionStatus.CANCELLED);
                    return subscriptionRepository.save(subscription);
                }).orElseThrow(() -> new SubscriptionNotFoundException(id));
    }
}
