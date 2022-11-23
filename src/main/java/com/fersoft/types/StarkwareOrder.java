package com.fersoft.types;

import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;


@Getter
@ToString
public class StarkwareOrder {

    private final StarkwareAmounts starkwareAmounts;
    private final StarkwareOrderType orderType;
    private final BigInteger quantumsAmountFee;
    private final BigInteger assetIdFee;
    private final String positionId;
    private final BigInteger nonce;
    private final Integer expirationEpochHours;

    public StarkwareOrder(StarkwareAmounts starkwareAmounts, StarkwareOrderType orderType, BigInteger quantumsAmountFee, BigInteger assetIdFee, String positionId, BigInteger nonce, Integer expirationEpochHours) {
        this.starkwareAmounts = starkwareAmounts;
        this.orderType = orderType;
        this.quantumsAmountFee = quantumsAmountFee;
        this.assetIdFee = assetIdFee;
        this.positionId = positionId;
        this.nonce = nonce;
        this.expirationEpochHours = expirationEpochHours;
    }
}
