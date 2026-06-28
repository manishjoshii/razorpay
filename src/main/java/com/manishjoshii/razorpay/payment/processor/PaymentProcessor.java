package com.manishjoshii.razorpay.payment.processor;

import com.manishjoshii.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.manishjoshii.razorpay.payment.processor.dto.PaymentProcessorResponse;

public interface PaymentProcessor {

    PaymentProcessorResponse charge(PaymentProcessorRequest request);

}
