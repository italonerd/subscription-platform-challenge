package com.example.subscription_platform_challenge.queue;

import com.example.subscription_platform_challenge.dto.PaymentDTO;
import com.example.subscription_platform_challenge.mapper.PaymentMapper;
import com.example.subscription_platform_challenge.model.enums.PaymentStatus;
import com.example.subscription_platform_challenge.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class PaymentQueueConsumer {
    private final PaymentService paymentService;
    private final PaymentMapper mapper;

    public PaymentQueueConsumer(PaymentService paymentService, PaymentMapper mapper) {
        this.paymentService = paymentService;
        this.mapper = mapper;
    }

    @RabbitListener(queues = {"${queue.name}"})
    public void processPayment(@Payload PaymentDTO paymentDTO) {
        paymentDTO.setStatus(PaymentStatus.randomValue().getStatus());
        paymentDTO.setPaymentDate(LocalDateTime.now());
        paymentService.update(paymentDTO.getId(), mapper.toPayment(paymentDTO));
        log.info("[processPayment] Process completed for payment " + paymentDTO.getId());
    }


}