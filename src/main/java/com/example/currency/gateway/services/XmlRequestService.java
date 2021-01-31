package com.example.currency.gateway.services;

import com.example.currency.gateway.api.controller.dto.xmlcurrent.CommandId;
import com.example.currency.gateway.api.controller.dto.xmlhistory.HistoryCommandId;
import com.example.currency.gateway.domain.EuroRates;
import com.example.currency.gateway.domain.XmlRequest;
import com.example.currency.gateway.exceptions.RequestIdAlreadyExistsException;
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

    public Optional<EuroRates> createCurrentRequest(CommandId commandId) {
        if (!xmlRequestRepository.existsById(commandId.getId())) {
            XmlRequest xmlRequestToSave = new XmlRequest(commandId.getId(), commandId.getField().getConsumer(),
                    commandId.getField().getCurrency().getValue());
            xmlRequestRepository.save(xmlRequestToSave);
        } else {
            throw new RequestIdAlreadyExistsException("A request with this id already exists in the database!");
        }
        return euroRatesService.getCurrentEuroRates();
    }

    public List<EuroRates> createHistoryRequest(HistoryCommandId historyCommandId) {
        if (!xmlRequestRepository.existsById(historyCommandId.getId())) {
            XmlRequest xmlRequestToSave = new XmlRequest(historyCommandId.getId(), historyCommandId.getHistory().getConsumer(),
                    historyCommandId.getHistory().getCurrency());
            xmlRequestRepository.save(xmlRequestToSave);
        } else {
            throw new RequestIdAlreadyExistsException("A request with this id already exists in the database!");
        }
        return euroRatesService.getHistoricalEuroRates(historyCommandId.getHistory().getPeriod());
    }
}
