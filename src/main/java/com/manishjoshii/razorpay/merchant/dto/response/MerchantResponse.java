package com.manishjoshii.razorpay.merchant.dto.response;

import com.manishjoshii.razorpay.common.enums.BusinessType;
import com.manishjoshii.razorpay.common.enums.MerchantStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record MerchantResponse(
        UUID id,
        String name,
        String email,
        String businessName,
        BusinessType businessType,
        MerchantStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
