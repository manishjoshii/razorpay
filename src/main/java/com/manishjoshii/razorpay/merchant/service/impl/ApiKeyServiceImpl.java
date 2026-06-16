package com.manishjoshii.razorpay.merchant.service.impl;

import com.manishjoshii.razorpay.common.exceptions.ResourceNotFoundException;
import com.manishjoshii.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.manishjoshii.razorpay.merchant.dto.response.CreateApiKeyResponse;
import com.manishjoshii.razorpay.merchant.entity.ApiKey;
import com.manishjoshii.razorpay.merchant.entity.Merchant;
import com.manishjoshii.razorpay.merchant.repository.ApiKeyRepository;
import com.manishjoshii.razorpay.merchant.repository.AppUserRepository;
import com.manishjoshii.razorpay.merchant.repository.MerchantRepository;
import com.manishjoshii.razorpay.merchant.service.ApiKeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public CreateApiKeyResponse create(UUID merchantId, CreateApiKeyRequest request) {
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(() -> new ResourceNotFoundException("merchant", merchantId));

//        String keyId = "rzp_" + request.environment().name().toUpperCase() + "_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String keyId = "rzp_" + request.environment().name().toUpperCase() + "Big_Random_String"; // TODO: replace string generation with a more robust and secure method
        String rawSecret = "Big_Random_Secret"; // TODO: replace with cryptographic secret

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(rawSecret)  // TODO: Encode with BcryptPasswordHash
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);
        return new CreateApiKeyResponse(apiKey.getId(), apiKey.getKeyId(), rawSecret, apiKey.getEnvironment());
    }
}
