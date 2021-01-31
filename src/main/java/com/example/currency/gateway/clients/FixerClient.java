package com.example.currency.gateway.clients;

import com.example.currency.gateway.clients.dto.ExchangeRates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class FixerClient {
    RestTemplate restTemplate;

    public FixerClient(RestTemplate restTemplate) {
        this.restTemplate = new RestTemplate();
    }

    public ExchangeRates getExchangeRates() {
        String uri = "http://data.fixer.io/api/latest?access_key=e49bd5bd79a6c6ba91ede532f6880224&base=EUR&symbols=GBP,USD,BGN";
        ExchangeRates rates = this.restTemplate.getForObject(uri, ExchangeRates.class);
        System.out.println("Rates:" + rates.getRates());
        return rates;
    }

    public ExchangeRates getSixMonthsOldExchangeRates() {
        LocalDate today = LocalDate.now();
        LocalDate sixMonthsAgo = today.minusMonths(6);
        String uri = "http://data.fixer.io/api/" + sixMonthsAgo + "?access_key=e49bd5bd79a6c6ba91ede532f6880224&base=EUR&symbols=USD,GBP,BGN";
        ExchangeRates rates = this.restTemplate.getForObject(uri, ExchangeRates.class);
        System.out.println("6-month old  rates:" + rates.getRates());
        return rates;
    }


}
