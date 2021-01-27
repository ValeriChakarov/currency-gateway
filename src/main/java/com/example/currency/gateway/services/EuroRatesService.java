package com.example.currency.gateway.services;

import com.example.currency.gateway.clients.FixerClient;
import com.example.currency.gateway.clients.dto.ExchangeRates;
import com.example.currency.gateway.domain.EuroRates;
import com.example.currency.gateway.repositories.EuroRatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Scheduled(fixedRate = 600000)
    public void mapRestObjecttoDB() {
        RestTemplate restTemplate = new RestTemplate();
        FixerClient fixerClient = new FixerClient(restTemplate);
        ExchangeRates exchangeRates = fixerClient.getExchangeRates();
        EuroRates euroRates = new EuroRates(Instant.now(), exchangeRates.getRates().get("GBP"),
                exchangeRates.getRates().get("USD"), exchangeRates.getRates().get("AUD"), exchangeRates.getRates().get("NZD"),
                exchangeRates.getRates().get("CAD"), exchangeRates.getRates().get("JPY"), exchangeRates.getRates().get("CHF"),
                exchangeRates.getRates().get("BGN"));
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
}
