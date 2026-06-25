package com.manishjoshii.razorpay.merchant.mapper;

import com.manishjoshii.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.manishjoshii.razorpay.merchant.dto.response.ApiKeyResponse;
import com.manishjoshii.razorpay.merchant.entity.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyMapper {
    ApiKeyCreateResponse toCreateResponse(ApiKey apiKey);

    List<ApiKeyResponse> toResponseList(List<ApiKey> apiKeyList);
}
