package com.manishjoshii.razorpay.merchant.controller;

import com.manishjoshii.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.manishjoshii.razorpay.merchant.dto.response.ApiKeyResponse;
import com.manishjoshii.razorpay.merchant.dto.response.CreateApiKeyResponse;
import com.manishjoshii.razorpay.merchant.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<CreateApiKeyResponse> createApiKey(@PathVariable UUID merchantId, @Valid @RequestBody CreateApiKeyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyService.create(merchantId, request));
    }

    @GetMapping
    public ResponseEntity<List<ApiKeyResponse>> getApiKeyListByMerchant(@PathVariable UUID merchantId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyService.getApiKeyListByMerchant(merchantId));
    }

    @DeleteMapping("/{keyId}")
    public ResponseEntity<Void> deleteApiKey(@PathVariable UUID merchantId, @PathVariable UUID keyId) {
        apiKeyService.revoke(merchantId, keyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{keyId}/rotate")
    public ResponseEntity<CreateApiKeyResponse> rotateKey(@PathVariable UUID merchantId, @PathVariable UUID keyId) {
        return ResponseEntity.status(200).body(apiKeyService.rotate(merchantId, keyId));
    }
}
