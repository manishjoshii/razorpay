package com.manishjoshii.razorpay.merchant.entity;

import com.manishjoshii.razorpay.common.entity.BaseEntity;
import com.manishjoshii.razorpay.common.enums.BusinessType;
import com.manishjoshii.razorpay.common.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "merchant",
        indexes = {
                @Index(name = "idx_merchant_status", columnList = "status")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Merchant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String contactNumber;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private BusinessType businessType;
    @Column(length = 100)
    private String businessName;
    @Column(length = 200)
    private String websiteUrl;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private MerchantStatus status = MerchantStatus.PENDING_KYC;

    @Column(length = 20)
    private String gstId;
    @Column(length = 20)
    private String panId;

    private String settlementBankAccount;
    private String settlementBankIFSC;
    private String getSettlementBankAccountHolderName;
}
