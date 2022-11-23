package com.fersoft.types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class OrderWithNonceAndPrice extends OrderWithNonce {
    private final String humanPrice;

    public OrderWithNonceAndPrice(Order order, BigInteger nonce, String humanPrice) {
        super(order, nonce);
        this.humanPrice = humanPrice;
    }

    public String getHumanPrice() {
        return humanPrice;
    }

    @Override
    protected boolean throwException() {
        return false;
    }

    @Override
    protected RoundingMode getRoundingMode() {
        return getOrder().getSide() == StarkwareOrderSide.BUY ? RoundingMode.UP :RoundingMode.DOWN;
    }

    @Override
    protected String getHumanAmount() {
        return new BigDecimal(getOrder().getHumanSize()).
                multiply(new BigDecimal(humanPrice)).toString();
    }
}
