package com.example.subscription_platform_challenge.config;

import com.example.subscription_platform_challenge.model.Payment;
import com.example.subscription_platform_challenge.model.Subscription;
import com.example.subscription_platform_challenge.model.enums.PaymentStatus;
import com.example.subscription_platform_challenge.model.enums.SubscriptionStatus;
import com.example.subscription_platform_challenge.repository.PaymentRepository;
import com.example.subscription_platform_challenge.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
@EnableJpaAuditing
@Slf4j
public class LocalDataBaseConfig {
    @Bean
    CommandLineRunner initDatabase(SubscriptionRepository subscriptionRepository, PaymentRepository paymentRepository) {
        return args -> {
            Subscription subscription = subscriptionRepository.save(
                    new Subscription(
                            67890L,
                            "PREMIUM",
                            SubscriptionStatus.ACTIVE,
                            LocalDateTime.now(),
                            LocalDateTime.now()));
            log.info("Preloading " + subscription);

            log.info("Preloading " + paymentRepository.save(
                    new Payment(
                            subscription,
                            new BigDecimal("49.99"),
                            PaymentStatus.APPROVED,
                            "CREDIT_CARD",
                            LocalDateTime.now()
                    )));
        };
    }
}
