package com.example.subscription_platform_challenge.integration;

import com.example.subscription_platform_challenge.dto.PaymentDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "external-payment-api-service", url = "${external.api.base.url}")
public interface ExternalPaymentApiClient {

    @PutMapping("/payments/process")
    default void processPayment(@RequestBody PaymentDTO paymentDTO, AmqpTemplate queueSender) {
        /* Mocking external API service */
        queueSender.convertAndSend("rpe.exchange", "routing-key-rpe", paymentDTO);
    }
}
