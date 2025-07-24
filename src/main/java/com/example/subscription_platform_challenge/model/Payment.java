package com.example.subscription_platform_challenge.model;

import com.example.subscription_platform_challenge.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
    @Column(precision = 19, scale = 2)
    private BigDecimal amount;
    private PaymentStatus status;
    private String method;
    private LocalDateTime paymentDate;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Payment(
            Subscription subscription, BigDecimal amount, PaymentStatus status, String method,
            LocalDateTime paymentDate) {
        this.subscription = subscription;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.paymentDate = paymentDate;
    }
}
