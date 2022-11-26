package com.fersoft.signature;

import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class DydxTestDigest extends BaseParamsDigest {

    private DydxTestDigest(String secretKey) {
        super(decodeBase64(secretKey), HMAC_SHA_256);
    }

    public static DydxTestDigest createInstance(String secretKey) {
        if (secretKey != null) {
            return new DydxTestDigest(secretKey);
        } else return null;
    }

    public String signMessage(String message){
        Mac mac256 = getMac();

        try {
            mac256.update(message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new ExchangeException("Digest encoding exception", e);
        }

        return Base64.getEncoder().encodeToString(mac256.doFinal());
    }

    @Override
    public String digestParams(RestInvocation restInvocation) {
        String requestBody = (restInvocation.getRequestBody() != null) ? restInvocation.getRequestBody() : "";
        String baseMessage = restInvocation.getParamValue(HeaderParam.class, "DYDX-TIMESTAMP") + restInvocation.getHttpMethod() + restInvocation.getPath();
        String midMessage = (!Objects.equals(restInvocation.getQueryString(), ""))
                ? "?"+restInvocation.getQueryString() + requestBody
                : requestBody;
        String finalMessage = baseMessage+midMessage;

        return signMessage(finalMessage);
    }
}
