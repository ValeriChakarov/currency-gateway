package com.example.currency.gateway.clients;

import com.example.currency.gateway.clients.dto.ExchangeRates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class FixerClient {
    RestTemplate restTemplate;

    public FixerClient(RestTemplate restTemplate) {
        this.restTemplate = new RestTemplate();
    }

    public ExchangeRates getExchangeRates() {
        String uri = "http://data.fixer.io/api/latest?access_key=a23721ac3e402f95434c2a277a38f7d9&base=EUR&symbols=GBP,USD,AUD,NZD,CAD,JPY,CHF";
        ExchangeRates rates = this.restTemplate.getForObject(uri, ExchangeRates.class);
        System.out.println("Rates:" + rates.getRates());
        return rates;
    }
}
