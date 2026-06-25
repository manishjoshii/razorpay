package com.manishjoshii.razorpay.merchant.dto.response;

import com.manishjoshii.razorpay.common.enums.Environment;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApiKeyResponse(
        UUID id,
        String keyId,
        Environment environment,
        boolean enabled,
        LocalDateTime lastUsedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
