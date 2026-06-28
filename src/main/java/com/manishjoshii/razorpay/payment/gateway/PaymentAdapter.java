package com.manishjoshii.razorpay.payment.gateway;

import com.manishjoshii.razorpay.payment.gateway.dto.PaymentRequest;
import com.manishjoshii.razorpay.payment.gateway.dto.PaymentResult;

import java.util.UUID;

public interface PaymentAdapter {
    PaymentResult initiate(PaymentRequest request);
}
