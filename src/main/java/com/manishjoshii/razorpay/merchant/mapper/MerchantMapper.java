package com.manishjoshii.razorpay.merchant.mapper;

import com.manishjoshii.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.manishjoshii.razorpay.merchant.dto.response.MerchantResponse;
import com.manishjoshii.razorpay.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MerchantMapper {

    Merchant toEntityFromSignupRequest(MerchantSignupRequest signupRequest);

    MerchantResponse toResponse(Merchant merchant);
}
