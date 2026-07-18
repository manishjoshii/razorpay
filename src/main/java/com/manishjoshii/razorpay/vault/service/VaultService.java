package com.manishjoshii.razorpay.vault.service;

import com.manishjoshii.razorpay.common.entity.Money;
import com.manishjoshii.razorpay.payment.processor.dto.PaymentProcessorResponse;
import com.manishjoshii.razorpay.vault.dto.request.TokenizeRequest;
import com.manishjoshii.razorpay.vault.dto.response.TokenizeResponse;

import java.util.Map;
import java.util.UUID;

public interface VaultService {
    TokenizeResponse tokenize(TokenizeRequest request, UUID merchantId);

    PaymentProcessorResponse charge(UUID paymentId, String token, Money amount, Map<String, Object> methodDetails);
}
