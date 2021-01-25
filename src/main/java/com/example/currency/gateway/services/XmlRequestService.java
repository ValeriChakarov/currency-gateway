package com.example.currency.gateway.services;

import com.example.currency.gateway.api.controller.dto.XmlRequestCurrent;
import com.example.currency.gateway.api.controller.dto.XmlRequestHistory;
import com.example.currency.gateway.domain.EuroRates;
import com.example.currency.gateway.domain.XmlRequest;
import com.example.currency.gateway.repositories.XmlRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class XmlRequestService {

    @Autowired
    XmlRequestRepository xmlRequestRepository;

    @Autowired
    EuroRatesService euroRatesService;

    public Optional<EuroRates> createCurrentRequest(XmlRequestCurrent xmlRequestCurrent) {
        if (!xmlRequestRepository.existsById(xmlRequestCurrent.getId())) {
            XmlRequest xmlRequestToSave = new XmlRequest(xmlRequestCurrent.getId(), xmlRequestCurrent.getConsumer(),
                    xmlRequestCurrent.getCurrency());
            xmlRequestRepository.save(xmlRequestToSave);
        } else {
            throw new RequestIdAlreadyExistsException("A request with this id already exists in the database!");
        }
        return euroRatesService.getCurrentEuroRates();
    }
    public List<EuroRates> createHistoryRequest(XmlRequestHistory xmlRequestHistory) {
        if (!xmlRequestRepository.existsById(xmlRequestHistory.getId())) {
            XmlRequest xmlRequestToSave = new XmlRequest(xmlRequestHistory.getId(), xmlRequestHistory.getConsumer(),
                    xmlRequestHistory.getCurrency());
            xmlRequestRepository.save(xmlRequestToSave);
        } else {
            throw new RequestIdAlreadyExistsException("A request with this id already exists in the database!");
        }
        return euroRatesService.getHistoricalEuroRates(xmlRequestHistory.getPeriod());
    }

}
