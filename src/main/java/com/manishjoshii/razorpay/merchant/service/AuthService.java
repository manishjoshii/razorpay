package com.manishjoshii.razorpay.merchant.service;

import com.manishjoshii.razorpay.merchant.dto.request.LoginRequest;
import com.manishjoshii.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.manishjoshii.razorpay.merchant.dto.response.LoginResponse;
import com.manishjoshii.razorpay.merchant.dto.response.MerchantResponse;
import jakarta.validation.Valid;

public interface AuthService {
    MerchantResponse signup(@Valid MerchantSignupRequest request);

    LoginResponse login(@Valid LoginRequest request);
}
