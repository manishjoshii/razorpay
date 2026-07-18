package com.manishjoshii.razorpay.payment.gateway.config;

import com.manishjoshii.razorpay.common.enums.PaymentMethod;
import com.manishjoshii.razorpay.payment.gateway.PaymentAdapter;
import com.manishjoshii.razorpay.payment.gateway.adapter.CardPaymentAdapter;
import com.manishjoshii.razorpay.payment.gateway.adapter.NetBankingAdapter;
import com.manishjoshii.razorpay.payment.gateway.adapter.UpiPaymentAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PaymentAdapterConfig {

    private final NetBankingAdapter netBankingAdapter;
    private final UpiPaymentAdapter upiPaymentAdapter;
    private final CardPaymentAdapter cardPaymentAdapter;

    @Bean
    public Map<PaymentMethod, PaymentAdapter> paymentAdapterMap() {
        return Map.of(
                PaymentMethod.CARD, cardPaymentAdapter,
                PaymentMethod.NETBANKING, netBankingAdapter,
                PaymentMethod.UPI, upiPaymentAdapter
        );
    }
}
