package com.manishjoshii.razorpay.merchant.dto.response;

import com.manishjoshii.razorpay.common.enums.Environment;

import java.util.UUID;

public record CreateApiKeyResponse(
        UUID id,
        String keyId,
        String keySecret,
        Environment environment
) {
}
