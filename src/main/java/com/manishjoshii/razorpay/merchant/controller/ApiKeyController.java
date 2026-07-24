package com.manishjoshii.razorpay.merchant.controller;

import com.manishjoshii.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.manishjoshii.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.manishjoshii.razorpay.merchant.dto.response.ApiKeyResponse;
import com.manishjoshii.razorpay.merchant.security.MerchantContext;
import com.manishjoshii.razorpay.merchant.service.ApiKeyService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchants/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;
    private final MerchantContext merchantContext;

    @PostMapping
    public ResponseEntity<ApiKeyCreateResponse> createApiKey(@Valid @RequestBody CreateApiKeyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyService.create(merchantContext.getMerchantId(), request));
    }

    @GetMapping
    public ResponseEntity<List<ApiKeyResponse>> getApiKeyListByMerchant() {
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyService.getApiKeyListByMerchant(merchantContext.getMerchantId()));
    }

    @DeleteMapping("/{keyId}")
    public ResponseEntity<Void> deleteApiKey(@PathVariable UUID keyId) {
        apiKeyService.revoke(merchantContext.getMerchantId(), keyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{keyId}/rotate")
    public ResponseEntity<ApiKeyCreateResponse> rotateKey(@PathVariable UUID keyId) {
        return ResponseEntity.status(200).body(apiKeyService.rotate(merchantContext.getMerchantId(), keyId));
    }
}
