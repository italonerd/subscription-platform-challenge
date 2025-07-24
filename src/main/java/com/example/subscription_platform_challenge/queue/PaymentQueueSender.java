package com.example.subscription_platform_challenge.queue;

import com.example.subscription_platform_challenge.dto.PaymentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentQueueSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void processPayment(PaymentDTO paymentDTO) {
        rabbitTemplate.convertAndSend(this.queue.getName(), paymentDTO);
        log.info("[processPayment] Process request sent for payment " + paymentDTO.getId());
    }
}
