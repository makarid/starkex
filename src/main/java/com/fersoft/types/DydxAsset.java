package com.fersoft.types;

import java.math.BigInteger;

public class DydxAsset {

    public static final DydxAsset USDC = new DydxAsset("USDC","0","1000000");
    private final String currency;
    private final BigInteger syntheticAssetId;
    private final int assetResolution;

    public DydxAsset(String currency, String syntheticAssetId, String assetResolution) {
        this.currency = currency;
        this.syntheticAssetId = (syntheticAssetId.length() > 4)
                ? new BigInteger(syntheticAssetId.substring(2),16)
                : new BigInteger(syntheticAssetId,16);
        this.assetResolution = assetResolution.length()-1;
    }

    public String getCurrency() {
        return currency;
    }

    public BigInteger getSyntheticAssetId() {
        return syntheticAssetId;
    }

    public int getAssetResolution() {
        return assetResolution;
    }

    @Override
    public String toString() {
        return "DydxAsset{" +
                "currency='" + currency + '\'' +
                ", syntheticAssetId=" + syntheticAssetId +
                ", assetResolution=" + assetResolution +
                '}';
    }
}
