package com.example.currency.gateway.services;

import com.example.currency.gateway.clients.dto.ExchangeRates;
import com.example.currency.gateway.clients.FixerClient;
import com.example.currency.gateway.domain.EuroRates;
import com.example.currency.gateway.repositories.EuroRatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EuroRatesService {

    @Autowired
    EuroRatesRepository euroRatesRepository;

//    @PersistenceContext
//    EntityManager entityManager;

    @Scheduled(fixedRate = 60000)
    public void mapRestObjecttoDB() {
        RestTemplate restTemplate = new RestTemplate();
        FixerClient fixerClient = new FixerClient(restTemplate);
        ExchangeRates exchangeRates = fixerClient.getExchangeRates();
        euroRatesRepository.save(new EuroRates(Instant.now(), exchangeRates.getRates().get("GBP"),
                exchangeRates.getRates().get("USD"), exchangeRates.getRates().get("AUD"), exchangeRates.getRates().get("NZD"),
                exchangeRates.getRates().get("CAD"), exchangeRates.getRates().get("JPY"), exchangeRates.getRates().get("CHF")));
    }

    public Optional<EuroRates> getCurrentEuroRates(){
        return euroRatesRepository.getCurrentEuroRates();
    }

    public List<EuroRates> getHistoricalEuroRates(Integer period){
        Instant endTime = Instant.now();
        Instant startTime = endTime.minus(period, ChronoUnit.HOURS);
        return euroRatesRepository.getHistoricalEuroRates(startTime, endTime);
    }
}
