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

//    @Autowired
//    RabbitMQSender rabbitMQSender;

    @Autowired
    private AmqpTemplate amqpTemplate;

    //    @PersistenceContext
//    EntityManager entityManager;
    @Value("${rates.collector.rabbitmq.queue}")
    String ratesCollectorQueueName;
    @Value("${statistics.collector.rabbitmq.queue}")
    String statisticsCollectorQueueName;

    @Value("${rates.collector.rabbitmq.routingkey}")
    private String ratesCollectorRoutingkey;
    @Value("${statistics.collector.rabbitmq.routingkey}")
    private String statisticsCollectorRoutingkey;

    @Value("${gateway.rabbitmq.exchange}")
    String directExchange;

    /**
     * Scheduled to run once an hour.
     */
    @Scheduled(fixedRate = 3600000)
    public void mapRestObjecttoDB() {
        RestTemplate restTemplate = new RestTemplate();
        FixerClient fixerClient = new FixerClient(restTemplate);
        ExchangeRates exchangeRates = fixerClient.getExchangeRates();
        EuroRates euroRates = new EuroRates(Instant.now(), exchangeRates.getRates().get("GBP"),
                exchangeRates.getRates().get("USD"), exchangeRates.getRates().get("BGN"));
        euroRatesRepository.save(euroRates);
        amqpTemplate.convertAndSend(directExchange, ratesCollectorRoutingkey, euroRates);
    }

    public Optional<EuroRates> getCurrentEuroRates() {
        return euroRatesRepository.getCurrentEuroRates();
    }

    public List<EuroRates> getHistoricalEuroRates(Integer period) {
        Instant endTime = Instant.now();
        Instant startTime = endTime.minus(period, ChronoUnit.HOURS);
        amqpTemplate.convertAndSend(directExchange, statisticsCollectorRoutingkey, euroRatesRepository.getHistoricalEuroRates(startTime, endTime));
        return euroRatesRepository.getHistoricalEuroRates(startTime, endTime);
    }

    /**
     * Scheduled to run once a week
     */
    @Scheduled(fixedRate = 604800000)
    public void getMargins() {
        MathContext mc = new MathContext(2);
        RestTemplate restTemplate = new RestTemplate();
        FixerClient fixerClient = new FixerClient(restTemplate);
        ExchangeRates exchangeRates = fixerClient.getExchangeRates();
        ExchangeRates sixMonthsOldRates = fixerClient.getSixMonthsOldExchangeRates();
        BigDecimal usdDifference = exchangeRates.getRates().get("USD").subtract(sixMonthsOldRates.getRates().get("USD"));
        BigDecimal usdMargin = usdDifference.divide(sixMonthsOldRates.getRates().get("USD"), 3, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100),mc);

        BigDecimal gbpDifference = exchangeRates.getRates().get("GBP").subtract(sixMonthsOldRates.getRates().get("GBP"));
        BigDecimal gbpMargin = gbpDifference.divide(sixMonthsOldRates.getRates().get("GBP"), 3, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100),mc);

        BigDecimal bgnDifference = exchangeRates.getRates().get("BGN").subtract(sixMonthsOldRates.getRates().get("BGN"));
        BigDecimal bgnMargin = bgnDifference.divide(sixMonthsOldRates.getRates().get("BGN"), 3, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100),mc);

        EuroMargins euroMargins = new EuroMargins(LocalDate.now(), usdMargin, gbpMargin, bgnMargin);
        euroMarginsRepository.save(euroMargins);
    }

}
