package com.manishjoshii.razorpay.payment.gateway.adapter;

import com.manishjoshii.razorpay.payment.gateway.PaymentAdapter;
import com.manishjoshii.razorpay.payment.gateway.dto.PaymentRequest;
import com.manishjoshii.razorpay.payment.gateway.dto.PaymentResult;

import java.util.UUID;

public class NetBankingAdapter implements PaymentAdapter {

    @Override
    public PaymentResult initiate(PaymentRequest request) {
        return null;
    }
}
