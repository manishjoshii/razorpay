package com.manishjoshii.razorpay.payment.service.impl;

import com.manishjoshii.razorpay.common.enums.OrderStatus;
import com.manishjoshii.razorpay.common.exceptions.DuplicateResourceException;
import com.manishjoshii.razorpay.payment.dto.request.CreateOrderRequest;
import com.manishjoshii.razorpay.payment.dto.response.OrderResponse;
import com.manishjoshii.razorpay.payment.entity.OrderRecord;
import com.manishjoshii.razorpay.payment.repository.OrderRepository;
import com.manishjoshii.razorpay.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Value("${payment.order.default-order-expiry-minutes:30}")
    private int defaultOrderExpiryMinutes;

    @Override
    public OrderResponse create(UUID merchantId, CreateOrderRequest request) {
        if (orderRepository.existsByMerchantIdAndReceipt(merchantId, request.receipt())) {
            throw new DuplicateResourceException("ORDER_RECEIPT_DUPLICATE", "Order with receipt already exists " + request.receipt());
        }
        OrderRecord orderRecord = OrderRecord.builder()
                .receipt(request.receipt())
                .amount(request.amount())
                .merchantId(merchantId)
                .notes(request.notes())
                .orderStatus(OrderStatus.CREATED)
                .expiresAt(request.expiresAt() != null ? request.expiresAt() :
                        LocalDateTime.now().plusMinutes(defaultOrderExpiryMinutes))
                .build();
        orderRecord = orderRepository.save(orderRecord);

        // TODO: publish Kafka event 'order_created'

        return new OrderResponse(orderRecord.getId(), orderRecord.getMerchantId(),
                orderRecord.getReceipt(), orderRecord.getAmount(),
                orderRecord.getOrderStatus(), orderRecord.getAttempts(),
                orderRecord.getNotes(), orderRecord.getExpiresAt(),
                null);
    }
}
