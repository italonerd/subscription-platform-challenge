package com.example.subscription_platform_challenge.service;

import com.example.subscription_platform_challenge.model.Payment;
import com.example.subscription_platform_challenge.model.enums.PaymentStatus;
import com.example.subscription_platform_challenge.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> get(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment register(Payment payment) {
        payment.setStatus(PaymentStatus.PENDING);
        return paymentRepository.save(payment);
    }

    public Payment update(Long id, Payment newPayment) {
        return paymentRepository.findById(id)
                .map(pay -> {
                    pay.setSubscription(
                            Optional.ofNullable(newPayment.getSubscription()).orElse(pay.getSubscription()));
                    pay.setAmount(Optional.ofNullable(newPayment.getAmount()).orElse(pay.getAmount()));
                    pay.setStatus(Optional.ofNullable(newPayment.getStatus()).orElse(pay.getStatus()));
                    pay.setMethod(Optional.ofNullable(newPayment.getMethod()).orElse(pay.getMethod()));
                    pay.setPaymentDate(Optional.ofNullable(newPayment.getPaymentDate()).orElse(pay.getPaymentDate()));

                    return paymentRepository.save(pay);
                })
                .orElseGet(() -> paymentRepository.save(newPayment));
    }
}
