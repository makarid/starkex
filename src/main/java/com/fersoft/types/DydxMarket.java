package com.fersoft.types;

public class DydxMarket {
    private final String value;
    private final DydxAsset asset;

    public DydxMarket(String value, DydxAsset asset) {
        this.value = value;
        this.asset = asset;
    }

    @Override
    public String toString() {
        return value;
    }

    public DydxAsset getAsset() {
        return asset;
    }
}

