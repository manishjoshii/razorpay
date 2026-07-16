package com.manishjoshii.razorpay.payment.processor.strategy;

import com.manishjoshii.razorpay.common.util.RandomizerUtil;
import com.manishjoshii.razorpay.payment.processor.PaymentProcessor;
import com.manishjoshii.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.manishjoshii.razorpay.payment.processor.dto.PaymentProcessorResponse;

public class UpiPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {

        final String VPA_CODE_FAIL = "fail@okaxis";

        String vpaCode = request.methodDetails() != null ?
                request.methodDetails().get("vpa").toString() : null;

        // simulation
        if (VPA_CODE_FAIL.equals(vpaCode)) {
            return new PaymentProcessorResponse.Failure("UPI_REJECTED", "Bank rejected transaction registration");
        }

        String processorRef = "UPI_Processor_" + RandomizerUtil.randomBase64(16);
        String bankRef = "BANK_REF_" + RandomizerUtil.randomBase64(16);


        return new PaymentProcessorResponse.Success(processorRef, bankRef);
    }
}
