package com.manishjoshii.razorpay.merchant.service.impl;

import com.manishjoshii.razorpay.common.exceptions.ResourceNotFoundException;
import com.manishjoshii.razorpay.common.util.RandomizerUtil;
import com.manishjoshii.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.manishjoshii.razorpay.merchant.dto.response.ApiKeyResponse;
import com.manishjoshii.razorpay.merchant.dto.response.CreateApiKeyResponse;
import com.manishjoshii.razorpay.merchant.entity.ApiKey;
import com.manishjoshii.razorpay.merchant.entity.Merchant;
import com.manishjoshii.razorpay.merchant.repository.ApiKeyRepository;
import com.manishjoshii.razorpay.merchant.repository.AppUserRepository;
import com.manishjoshii.razorpay.merchant.repository.MerchantRepository;
import com.manishjoshii.razorpay.merchant.service.ApiKeyService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

        String keyId = "rzp_" + request.environment().name().toLowerCase() + "_" + RandomizerUtil.randomBase64(24);
        String rawSecret = RandomizerUtil.randomBase64(40);

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(rawSecret)  // TODO: Encode with BcryptPasswordHash
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);
        return new CreateApiKeyResponse(apiKey.getId(), apiKey.getKeyId(), rawSecret, apiKey.getEnvironment());
    }

    @Override
    public List<ApiKeyResponse> getApiKeyListByMerchant(UUID merchantId) {
        return apiKeyRepository.findByMerchantId(merchantId).stream()
                .map(apiKey -> new ApiKeyResponse(
                        apiKey.getId(),
                        apiKey.getKeyId(),
                        apiKey.getEnvironment(),
                        apiKey.isEnabled(),
                        apiKey.getLastUsedAt(),
                        null
                ))
                .toList();
    }

    @Override
    @Transactional
    public void revoke(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(k -> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("ApiKey", keyId));
        apiKey.setEnabled(false);
        apiKeyRepository.save(apiKey);
    }

    @Override
    @Transactional
    public CreateApiKeyResponse rotate(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(k -> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("ApiKey", keyId));

        String newRawSecret = RandomizerUtil.randomBase64(40);
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(newRawSecret); // TODO: Encode with BcryptPasswordEncoder
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodExpiresAt(LocalDateTime.now().plusHours(24)); // 24 hours grace period
        apiKey = apiKeyRepository.save(apiKey);

        return new CreateApiKeyResponse(apiKey.getId(), apiKey.getKeyId(), newRawSecret, apiKey.getEnvironment());
    }
}
