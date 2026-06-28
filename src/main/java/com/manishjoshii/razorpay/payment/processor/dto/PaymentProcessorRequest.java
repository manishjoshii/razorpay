package com.manishjoshii.razorpay.payment.processor.dto;

import com.manishjoshii.razorpay.common.entity.Money;
import com.manishjoshii.razorpay.common.enums.PaymentMethod;

import java.util.Map;

public record PaymentProcessorRequest(
        PaymentMethod method,
        Money amount,
        Map<String, String> methodDetails
) {
}
