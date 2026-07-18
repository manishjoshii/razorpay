package com.manishjoshii.razorpay.merchant.service.impl;

import com.manishjoshii.razorpay.common.enums.MerchantStatus;
import com.manishjoshii.razorpay.common.enums.UserRole;
import com.manishjoshii.razorpay.common.exceptions.DuplicateResourceException;
import com.manishjoshii.razorpay.common.exceptions.ResourceNotFoundException;
import com.manishjoshii.razorpay.merchant.dto.request.LoginRequest;
import com.manishjoshii.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.manishjoshii.razorpay.merchant.dto.response.LoginResponse;
import com.manishjoshii.razorpay.merchant.dto.response.MerchantResponse;
import com.manishjoshii.razorpay.merchant.entity.AppUser;
import com.manishjoshii.razorpay.merchant.entity.Merchant;
import com.manishjoshii.razorpay.merchant.mapper.MerchantMapper;
import com.manishjoshii.razorpay.merchant.repository.AppUserRepository;
import com.manishjoshii.razorpay.merchant.repository.MerchantRepository;
import com.manishjoshii.razorpay.merchant.security.JwtUtil;
import com.manishjoshii.razorpay.merchant.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;
    private final MerchantMapper merchantMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    

    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {
        if (merchantRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("DUPLICATE_MERCHANT", "Merchant with email already exists '" + request.email() + "'");
        }

        Merchant merchant = merchantMapper.toEntityFromSignupRequest(request);
        merchant.setStatus(MerchantStatus.PENDING_KYC);

        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .email(request.email())
                .merchant(merchant)
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(UserRole.OWNER)
                .build();

        appUserRepository.save(appUser);

        return merchantMapper.toResponse(merchant);
    }

	@Override
	@Transactional
	public LoginResponse login(@Valid LoginRequest request) {
	    authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.email(), request.password())
		);

	    AppUser appUser = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.email()));

        String token = jwtUtil.generateAccessToken(request.email(), appUser.getMerchant().getId(), appUser.getRole().toString());
       
        return new LoginResponse(token);
	}
}
