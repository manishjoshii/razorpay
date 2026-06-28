package com.manishjoshii.razorpay.payment.processor.strategy;

import com.manishjoshii.razorpay.payment.processor.PaymentProcessor;
import com.manishjoshii.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.manishjoshii.razorpay.payment.processor.dto.PaymentProcessorResponse;

public class NetBankingPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        return null;
    }
}
