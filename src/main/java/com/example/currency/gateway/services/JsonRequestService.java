package com.example.currency.gateway.services;

import com.example.currency.gateway.api.controller.dto.JsonRequestCurrent;
import com.example.currency.gateway.api.controller.dto.JsonRequestHistory;
import com.example.currency.gateway.domain.EuroRates;
import com.example.currency.gateway.domain.JsonRequest;
import com.example.currency.gateway.repositories.JsonRequestRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JsonRequestService {

    @Autowired
    JsonRequestRepository jsonRequestRepository;

    @Autowired
    EuroRatesService euroRatesService;

    public Optional<EuroRates> createCurrentRequest(@NotNull JsonRequestCurrent jsonRequestCurrent) {
        if (!jsonRequestRepository.existsById(jsonRequestCurrent.getRequestId())) {
            JsonRequest jsonRequest = new JsonRequest(jsonRequestCurrent.getRequestId(), jsonRequestCurrent.getTimestamp(),
                    jsonRequestCurrent.getClient(), jsonRequestCurrent.getCurrency());
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
            jsonRequestRepository.save(jsonRequest);
        } else {
            throw new RequestIdAlreadyExistsException("A request with this id already exists in the database!");
        }
        return euroRatesService.getHistoricalEuroRates(jsonRequestHistory.getPeriod());
    }
}
