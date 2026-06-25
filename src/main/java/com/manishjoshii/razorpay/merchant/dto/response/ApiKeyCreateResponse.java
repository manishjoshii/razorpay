package com.manishjoshii.razorpay.merchant.dto.response;

import com.manishjoshii.razorpay.common.enums.Environment;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApiKeyCreateResponse(
        UUID id,
        String keyId,
        String keySecret,
        Environment environment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
