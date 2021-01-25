package com.example.currency.gateway.api.controller;

import com.example.currency.gateway.api.controller.dto.JsonRequestCurrent;
import com.example.currency.gateway.api.controller.dto.JsonRequestHistory;
import com.example.currency.gateway.api.controller.dto.XmlRequestCurrent;
import com.example.currency.gateway.api.controller.dto.XmlRequestHistory;
import com.example.currency.gateway.domain.EuroRates;
import com.example.currency.gateway.domain.XmlRequest;
import com.example.currency.gateway.services.XmlRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/xml_api/command")
public class ExternalServiceTwo {

    @Autowired
    XmlRequestService xmlRequestService;

    @PostMapping(value ="/current", produces= MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Optional<EuroRates>> createRequestAndGetEURRates(@RequestBody XmlRequestCurrent xmlRequestCurrent){
        return ResponseEntity.ok(xmlRequestService.createCurrentRequest(xmlRequestCurrent));
    }

    @PostMapping("/history")
    public ResponseEntity<List<EuroRates>> createRequestAndGetHistoricalEURRates(@RequestBody XmlRequestHistory xmlRequestHistory){
        return ResponseEntity.ok(xmlRequestService.createHistoryRequest(xmlRequestHistory));
    }
}
