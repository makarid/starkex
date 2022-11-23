package com.fersoft.types;

import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@ToString
public class StarkwareAmounts {
    private final BigInteger quantumsAmountSynthetic;
    private final BigInteger quantumsAmountCollateral;
    private final BigInteger assetIdSynthetic;
    private final BigInteger assetIdCollateral;
    private final boolean isBuyingSynthetic;

    public StarkwareAmounts(BigInteger quantumsAmountSynthetic, BigInteger quantumsAmountCollateral, BigInteger assetIdSynthetic, BigInteger assetIdCollateral, boolean isBuyingSynthetic) {
        this.quantumsAmountSynthetic = quantumsAmountSynthetic;
        this.quantumsAmountCollateral = quantumsAmountCollateral;
        this.assetIdSynthetic = assetIdSynthetic;
        this.assetIdCollateral = assetIdCollateral;
        this.isBuyingSynthetic = isBuyingSynthetic;
    }
}
