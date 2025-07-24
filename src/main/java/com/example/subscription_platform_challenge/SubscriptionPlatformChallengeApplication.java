package com.example.subscription_platform_challenge;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableRabbit
public class SubscriptionPlatformChallengeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubscriptionPlatformChallengeApplication.class, args);
    }
}
