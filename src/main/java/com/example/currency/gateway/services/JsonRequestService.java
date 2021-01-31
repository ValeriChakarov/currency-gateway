package com.example.currency.gateway.services;

import com.example.currency.gateway.api.controller.dto.json.JsonRequestCurrent;
import com.example.currency.gateway.api.controller.dto.json.JsonRequestHistory;
import com.example.currency.gateway.domain.EuroRates;
import com.example.currency.gateway.domain.JsonRequest;
import com.example.currency.gateway.exceptions.RequestIdAlreadyExistsException;
import com.example.currency.gateway.repositories.JsonRequestRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JsonRequestService {

    @Autowired
    JsonRequestRepository jsonRequestRepository;

    @Autowired
    EuroRatesService euroRatesService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${gateway.rabbitmq.exchange}")
    String directExchange;

    @Value("${request.current.rate.collector.rabbitmq.queue}")
    String requestCurrentRateCollectorQueueName;
    @Value("${request.historical.rate.collector.rabbitmq.queue}")
    String requestHistoricalRateCollectorQueueName;

    @Value("${request.current.rate.collector.rabbitmq.routingkey}")
    private String requestCurrentRateCollectorRoutingkey;
    @Value("${request.historical.rate.collector.rabbitmq.routingkey}")
    private String requestHistoricalRateCollectorRoutingkey;

    public Optional<EuroRates> createCurrentRequest(@NotNull JsonRequestCurrent jsonRequestCurrent) {
        if (!jsonRequestRepository.existsById(jsonRequestCurrent.getRequestId())) {
            JsonRequest jsonRequest = new JsonRequest(jsonRequestCurrent.getRequestId(), jsonRequestCurrent.getTimestamp(),
                    jsonRequestCurrent.getClient(), jsonRequestCurrent.getCurrency());
            amqpTemplate.convertAndSend(directExchange, requestCurrentRateCollectorRoutingkey, jsonRequest);
            jsonRequestRepository.save(jsonRequest);
        } else {
            throw new RequestIdAlreadyExistsException("A request with this id already exists in the database!");
        }
        return euroRatesService.getCurrentEuroRates();
    }


    public List<EuroRates> createHistoryRequest(@NotNull JsonRequestHistory jsonRequestHistory) {
        if (!jsonRequestRepository.existsById(jsonRequestHistory.getRequestId())) {
            JsonRequest jsonRequest = new JsonRequest(jsonRequestHistory.getRequestId(), jsonRequestHistory.getTimestamp(),
                    jsonRequestHistory.getClient(), jsonRequestHistory.getCurrency());
            amqpTemplate.convertAndSend(directExchange, requestHistoricalRateCollectorRoutingkey, jsonRequest);
            jsonRequestRepository.save(jsonRequest);
        } else {
            throw new RequestIdAlreadyExistsException("A request with this id already exists in the database!");
        }
        return euroRatesService.getHistoricalEuroRates(jsonRequestHistory.getPeriod());
    }
}
