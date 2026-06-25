package com.manishjoshii.razorpay.payment.mapper;

import com.manishjoshii.razorpay.payment.dto.response.OrderResponse;
import com.manishjoshii.razorpay.payment.entity.OrderRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    OrderResponse toResponse(OrderRecord orderRecord);
}
