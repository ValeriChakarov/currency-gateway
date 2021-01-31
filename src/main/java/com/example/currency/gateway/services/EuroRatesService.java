package com.example.currency.gateway.services;

import com.example.currency.gateway.clients.FixerClient;
import com.example.currency.gateway.clients.dto.ExchangeRates;
import com.example.currency.gateway.domain.EuroMargins;
import com.example.currency.gateway.domain.EuroRates;
import com.example.currency.gateway.repositories.EuroMarginsRepository;
import com.example.currency.gateway.repositories.EuroRatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EuroRatesService {

    @Autowired
    EuroRatesRepository euroRatesRepository;

    @Autowired
    EuroMarginsRepository euroMarginsRepository;

    //    @PersistenceContext
//    EntityManager entityManager;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${gateway.rabbitmq.exchange}")
    String directExchange;

    @Value("${rates.collector.rabbitmq.routingkey}")
    private String ratesCollectorRoutingkey;
    @Value("${margins.collector.rabbitmq.routingkey}")
    private String marginsCollectorRoutingkey;

    /**
     * Scheduled to run once an hour.
     */
    @Scheduled(fixedRate = 3600000)
    public void mapRestObjecttoDB() {
        RestTemplate restTemplate = new RestTemplate();
        FixerClient fixerClient = new FixerClient(restTemplate);
        ExchangeRates exchangeRates = fixerClient.getExchangeRates();
        EuroRates euroRates = new EuroRates(UUID.randomUUID(),Instant.now(), exchangeRates.getRates().get("GBP"),
                exchangeRates.getRates().get("USD"), exchangeRates.getRates().get("BGN"));
        amqpTemplate.convertAndSend(directExchange, ratesCollectorRoutingkey, euroRates);
        euroRatesRepository.save(euroRates);
        ;
    }

    /**
     * Scheduled to run once a week
     */
    @Scheduled(fixedRate = 604800000)
    public void getMargins() {
        EuroMargins euroMargins = new EuroMargins(LocalDate.now(), getSingleMargin("USD"), getSingleMargin("GBP"), getSingleMargin("BGN"));
        amqpTemplate.convertAndSend(directExchange, marginsCollectorRoutingkey, euroMargins);
        euroMarginsRepository.save(euroMargins);
    }


    public Optional<EuroRates> getCurrentEuroRates() {
        return euroRatesRepository.getCurrentEuroRates();
    }

    public List<EuroRates> getHistoricalEuroRates(Integer period) {
        Instant endTime = Instant.now();
        Instant startTime = endTime.minus(period, ChronoUnit.HOURS);
        return euroRatesRepository.getHistoricalEuroRates(startTime, endTime);
    }

    public BigDecimal getSingleMargin(String currency) {
        MathContext mc = new MathContext(2);
        RestTemplate restTemplate = new RestTemplate();
        FixerClient fixerClient = new FixerClient(restTemplate);
        ExchangeRates exchangeRates = fixerClient.getExchangeRates();
        ExchangeRates sixMonthsOldRates = fixerClient.getSixMonthsOldExchangeRates();
        BigDecimal usdDifference = exchangeRates.getRates().get(currency).subtract(sixMonthsOldRates.getRates().get(currency));
        BigDecimal usdMarginPercentage = usdDifference.divide(sixMonthsOldRates.getRates().get(currency), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100), mc);

        return usdMarginPercentage;
    }
}
