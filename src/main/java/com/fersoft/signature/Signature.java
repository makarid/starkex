package com.fersoft.signature;

import lombok.Getter;

import java.math.BigInteger;

/**
 * ECDSA signature representation
 *
 * @author ferat capar
 */
@Getter
public class Signature {

    private final BigInteger r;
    private final BigInteger s;

    public Signature(BigInteger r, BigInteger s) {
        this.r = r;
        this.s = s;
    }

    @Override
    public String toString() {
        return String.format("%1$64s%2$64s", this.r.toString(16), this.s.toString(16)).replace(" ", "0");
    }

}
