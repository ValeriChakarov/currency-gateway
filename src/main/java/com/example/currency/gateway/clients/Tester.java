package com.example.currency.gateway.clients;


import com.example.currency.gateway.clients.dto.ExchangeRates;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Tester {

    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        FixerClient client = new FixerClient(restTemplate);
//        client.getSixMonthsOldExchangeRates();
//        MathContext mc = new MathContext(2);
//        RestTemplate restTemplate = new RestTemplate();
//        FixerClient fixerClient = new FixerClient(restTemplate);
//        ExchangeRates exchangeRates = fixerClient.getExchangeRates();
//        ExchangeRates sixMonthsOldRates = fixerClient.getSixMonthsOldExchangeRates();
//        BigDecimal usdDifference = exchangeRates.getRates().get("USD").subtract(sixMonthsOldRates.getRates().get("USD"));
//        BigDecimal usdMargin = usdDifference.divide(sixMonthsOldRates.getRates().get("USD"), 3, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100),mc);
//        System.out.println(usdMargin);
    }
}
