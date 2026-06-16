package com.manishjoshii.razorpay.merchant.service.impl;

import com.manishjoshii.razorpay.common.enums.MerchantStatus;
import com.manishjoshii.razorpay.common.enums.UserRole;
import com.manishjoshii.razorpay.common.exceptions.DuplicateResourceException;
import com.manishjoshii.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.manishjoshii.razorpay.merchant.dto.response.MerchantResponse;
import com.manishjoshii.razorpay.merchant.entity.AppUser;
import com.manishjoshii.razorpay.merchant.entity.Merchant;
import com.manishjoshii.razorpay.merchant.repository.AppUserRepository;
import com.manishjoshii.razorpay.merchant.repository.MerchantRepository;
import com.manishjoshii.razorpay.merchant.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {
        if (merchantRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("DUPLICATE_MERCHANT", "Merchant with email already exists '" + request.email() + "'");
        }

        Merchant merchant = Merchant.builder()
                .name(request.name())
                .email(request.email())
                .businessName(request.businessName())
                .businessType(request.businessType())
                .status(MerchantStatus.PENDING_KYC)
                .build();

        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .email(request.email())
                .merchant(merchant)
                .passwordHash(request.password())  // TODO: encrypt password using bcrypt
                .role(UserRole.OWNER)
                .build();

        appUserRepository.save(appUser);

        return new MerchantResponse(
                merchant.getId(),
                merchant.getName(),
                merchant.getEmail(),
                merchant.getBusinessName(),
                merchant.getBusinessType(),
                merchant.getStatus()
        );
    }
}
