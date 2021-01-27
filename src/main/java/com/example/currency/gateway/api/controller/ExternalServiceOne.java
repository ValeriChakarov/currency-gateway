package com.example.currency.gateway.api.controller;

import com.example.currency.gateway.api.controller.dto.json.JsonRequestCurrent;
import com.example.currency.gateway.api.controller.dto.json.JsonRequestHistory;
import com.example.currency.gateway.domain.EuroRates;
import com.example.currency.gateway.services.JsonRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/json_api")
@RequiredArgsConstructor
public class ExternalServiceOne {

    @Autowired
    JsonRequestService jsonRequestService;

    @PostMapping("/current")
    public ResponseEntity<Optional<EuroRates>> createRequestAndGetEURRates(@RequestBody JsonRequestCurrent jsonCurrentRequest){
         return ResponseEntity.ok(jsonRequestService.createCurrentRequest(jsonCurrentRequest));
    }

    @PostMapping("/history")
    public ResponseEntity<List<EuroRates>> createRequestAndGetHistoricalEURRates(@RequestBody JsonRequestHistory jsonRequestHistory){
        return ResponseEntity.ok(jsonRequestService.createHistoryRequest(jsonRequestHistory));
    }
}
