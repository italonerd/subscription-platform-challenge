package com.example.subscription_platform_challenge.dto;

import com.example.subscription_platform_challenge.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class PaymentDTO {
    private Long id;
    private Long subscriptionId;
    private BigDecimal amount;
    private String status;
    private String method;
    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
