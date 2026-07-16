package com.manishjoshii.razorpay.payment.processor.strategy;

import com.manishjoshii.razorpay.common.util.RandomizerUtil;
import com.manishjoshii.razorpay.payment.processor.PaymentProcessor;
import com.manishjoshii.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.manishjoshii.razorpay.payment.processor.dto.PaymentProcessorResponse;

public class NetBankingPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {

        final String BANK_CODE_FAIL = "BANK_CODE_FAIL";

        String bankCode = request.methodDetails() != null ?
                request.methodDetails().get("BANK").toString() : null;

        // simulation
        if (BANK_CODE_FAIL.equals(bankCode)) {
            return new PaymentProcessorResponse.Failure("BANK_REJECTED", "Bank rejected transaction registration");
        }

        String processorRef = "NBK_Processor_" + RandomizerUtil.randomBase64(16);

        String redirectRef = "https://REDIRECT_BANK.com/" +processorRef;

        return new PaymentProcessorResponse.Success(processorRef, redirectRef);
    }
}
