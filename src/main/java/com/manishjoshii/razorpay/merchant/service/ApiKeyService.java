package com.manishjoshii.razorpay.merchant.service;

import com.manishjoshii.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.manishjoshii.razorpay.merchant.dto.response.CreateApiKeyResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface ApiKeyService {

    CreateApiKeyResponse create(UUID merchantId, CreateApiKeyRequest request);
}
