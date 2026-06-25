package com.manishjoshii.razorpay.payment.service.impl;

import com.manishjoshii.razorpay.common.enums.OrderStatus;
import com.manishjoshii.razorpay.common.exceptions.BusinessRuleViolationException;
import com.manishjoshii.razorpay.common.exceptions.DuplicateResourceException;
import com.manishjoshii.razorpay.common.exceptions.ResourceNotFoundException;
import com.manishjoshii.razorpay.payment.dto.request.CreateOrderRequest;
import com.manishjoshii.razorpay.payment.dto.response.OrderResponse;
import com.manishjoshii.razorpay.payment.dto.response.PaymentResponse;
import com.manishjoshii.razorpay.payment.entity.OrderRecord;
import com.manishjoshii.razorpay.payment.entity.Payment;
import com.manishjoshii.razorpay.payment.mapper.OrderMapper;
import com.manishjoshii.razorpay.payment.mapper.PaymentMapper;
import com.manishjoshii.razorpay.payment.repository.OrderRepository;
import com.manishjoshii.razorpay.payment.repository.PaymentRepository;
import com.manishjoshii.razorpay.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderMapper orderMapper;

    @Value("${payment.order.default-order-expiry-minutes:30}")
    private int defaultOrderExpiryMinutes;

    @Override
    @Transactional
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

        return orderMapper.toResponse(orderRecord);
    }

    @Override
    public OrderResponse getById(UUID merchantId, UUID orderId) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse cancel(UUID merchantId, UUID orderId) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        if (order.getOrderStatus().equals(OrderStatus.CANCELED) || order.getOrderStatus().equals(OrderStatus.PAID)) {    // using .equals method check if that is issue
            throw new BusinessRuleViolationException("ORDER_CANNOT_CANCEL", "Cannot cancel order with order status " + order.getOrderStatus().name());
        }

        order.setOrderStatus(OrderStatus.CANCELED);

        order = orderRepository.save(order);

        return orderMapper.toResponse(order);
    }

    @Override
    public List<PaymentResponse> listPayments(UUID merchantId, UUID orderId) {
        List<Payment> paymentList = paymentRepository.findByOrderId(orderId);

//        return paymentList.stream().map(
//                paymentMapper::toResponse          // payment -> paymentMapper.toResponse(payment);
//        ).collect(Collectors.toList());

        return paymentMapper.toResponseList(paymentList);
    }
}
