package com.fersoft.signature;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fersoft.exception.FieldExceedMaxException;
import com.fersoft.exception.HashingException;
import com.fersoft.exception.QuantumSizeException;
import com.fersoft.exception.SignException;
import com.fersoft.types.*;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.dydx.dydxExchange;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class DydxPlaceOrderTest {

    @Path("/v3/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    interface DydxTestAuthenticated {

        @POST
        @Path("orders")
        JsonNode placeDydxOrder(
                @HeaderParam("DYDX-SIGNATURE") ParamsDigest signature,
                @HeaderParam("DYDX-API-KEY") String apiKey,
                @HeaderParam("DYDX-TIMESTAMP") String timestamp,
                @HeaderParam("DYDX-PASSPHRASE") String passphrase,
                DydxOrderParams orderParams
        ) throws IOException;
    }

    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DydxOrderParams {

        private final String market;
        private final String side;
        private final String type;
        private final boolean postOnly;
        private final String size;
        private final String price;
        private final String limitFee;
        private final String expiration;
        private final String clientId;
        private final String signature;

        public DydxOrderParams(
                @JsonProperty("market") String market,
                @JsonProperty("side") String side,
                @JsonProperty("type") String type,
                @JsonProperty("postOnly") boolean postOnly,
                @JsonProperty("size") String size,
                @JsonProperty("price") String price,
                @JsonProperty("limitFee") String limitFee,
                @JsonProperty("expiration") String expiration,
                @JsonProperty("clientId") String clientId,
                @JsonProperty("signature") String signature) {
            this.market = market;
            this.side = side;
            this.type = type;
            this.postOnly = postOnly;
            this.size = size;
            this.price = price;
            this.limitFee = limitFee;
            this.expiration = expiration;
            this.clientId = clientId;
            this.signature = signature;
        }
    }

    static DydxTestAuthenticated dydx;
    static Properties properties;

    static DydxTestDigest digest;

//    @BeforeAll
    public static void setUp() {
        properties = new Properties();
        try {
            properties.load(DydxPlaceOrderTest.class.getResourceAsStream("/keys.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        digest = DydxTestDigest.createInstance(properties.getProperty("secret"));
        ExchangeSpecification spec = new dydxExchange().getDefaultExchangeSpecification();

        Map<String, Object> map = new HashMap<>();

        map.put("starkkey", properties.getProperty("starkkey"));

        spec.setApiKey(properties.getProperty("apikey"));
        spec.setSslUri("https://api.stage.dydx.exchange");
        spec.setSecretKey(properties.getProperty("secret"));
        spec.setUserName(properties.getProperty("passphrase"));
        spec.setExchangeSpecificParameters(map);

        dydx = ExchangeRestProxyBuilder.forInterface(
                        DydxTestAuthenticated.class, spec)
                .build();
    }

//    @Test
    public void placingADydxTestNetOrder() throws QuantumSizeException, NoSuchAlgorithmException, FieldExceedMaxException, HashingException, SignException, IOException {
        SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Instant now = Instant.now();
        String timestamp = isoDateFormat.format(Date.from(now));
        String expirationDate = isoDateFormat.format(Date.from(now.plusSeconds(100000)));
        String clientId = UUID.randomUUID().toString();

        String price = BigDecimal.valueOf(15).stripTrailingZeros().toString();
        String volume = BigDecimal.valueOf(0.1).stripTrailingZeros().toString();
        String limitFee = BigDecimal.valueOf(0.015).toString();
        DydxMarket symbol = DydxMarket.ETH_USD;
        StarkwareOrderSide side = StarkwareOrderSide.BUY;

        JsonNode response = dydx.placeDydxOrder(
                digest,
                properties.getProperty("apikey"),
                timestamp,
                properties.getProperty("passphrase"),
                new DydxOrderParams(
                        symbol.toString(),
                        side.toString(),
                        "LIMIT",
                        false,
                        volume,
                        price,
                        limitFee,
                        expirationDate,
                        clientId,
                        new StarkSigner().sign(new OrderWithClientIdWithPrice(
                                        new Order(
                                                properties.getProperty("positionId"),
                                                volume,
                                                limitFee,
                                                symbol,
                                                side,
                                                expirationDate
                                        ),
                                        clientId,
                                        price),
                                NetworkId.GOERLIC,
                                new BigInteger(properties.getProperty("starkkey"), 16)).toString())
        );
        System.out.println(response);
    }
}
