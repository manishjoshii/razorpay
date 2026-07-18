package com.manishjoshii.razorpay.payment.statemachine;

import com.manishjoshii.razorpay.common.enums.PaymentActor;
import com.manishjoshii.razorpay.common.enums.PaymentEvent;
import com.manishjoshii.razorpay.common.enums.PaymentStatus;
import com.manishjoshii.razorpay.payment.entity.Payment;
import com.manishjoshii.razorpay.payment.entity.PaymentTransitionLog;
import com.manishjoshii.razorpay.payment.repository.PaymentTransitionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentTransitionService {

    private final PaymentTransitionLogRepository paymentTransitionLogRepository;
    private final PaymentStateMachine paymentStateMachine;

    public PaymentStatus apply(Payment payment, PaymentEvent event) {
        PaymentStatus next = paymentStateMachine.transition(payment.getStatus(), event);

        PaymentTransitionLog paymentTransitionLog = PaymentTransitionLog.builder()
                .payment(payment)
                .fromStatus(payment.getStatus())
                .event(event)
                .toStatus(next)
                .actor(PaymentActor.SYSTEM) // TODO: fetch merchant context to identify actor\
                .occurredAt(LocalDateTime.now())
                .build();

        payment.setStatus(next);

        paymentTransitionLogRepository.save(paymentTransitionLog);

        return next;
    }

}
