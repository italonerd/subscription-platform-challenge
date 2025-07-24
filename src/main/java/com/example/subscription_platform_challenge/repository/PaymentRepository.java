package com.example.subscription_platform_challenge.repository;

import com.example.subscription_platform_challenge.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
