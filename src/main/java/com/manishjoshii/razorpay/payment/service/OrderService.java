package com.manishjoshii.razorpay.payment.service;


import com.manishjoshii.razorpay.payment.dto.request.CreateOrderRequest;
import com.manishjoshii.razorpay.payment.dto.response.OrderResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface OrderService {

    OrderResponse create(UUID merchantId, CreateOrderRequest request);
}
