package com.manishjoshii.razorpay.payment.entity;

import com.manishjoshii.razorpay.common.enums.PaymentEvent;
import com.manishjoshii.razorpay.common.enums.PaymentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class PaymentTransitionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private UUID paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_status", nullable = false)
    private PaymentStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_status", nullable = false)
    private PaymentStatus toStatus;


    @Enumerated(EnumType.STRING)
    @Column(name = "event", nullable = false)
    private PaymentEvent event;

    @Column(name = "actor", length = 100)
    @Enumerated(EnumType.STRING)
    private String actor;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;
}
