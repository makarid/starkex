package com.fersoft.types;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Order {

    private final String positionId;
    private final String humanSize;
    private final String limitFee;  // Max fee fraction, e.g. 0.01 is a max 1% fee.
    private final DydxMarket market;
    private final StarkwareOrderSide side;
    private final String expirationIsoTimestamp;

    public Order(String positionId, String humanSize, String limitFee, DydxMarket market, StarkwareOrderSide side, String expirationIsoTimestamp) {
        this.positionId = positionId;
        this.humanSize = humanSize;
        this.limitFee = limitFee;
        this.market = market;
        this.side = side;
        this.expirationIsoTimestamp = expirationIsoTimestamp;
    }
}
