package com.example.currency.gateway.api.controller;

import com.example.currency.gateway.api.controller.dto.json.JsonRequestCurrent;
import com.example.currency.gateway.api.controller.dto.json.JsonRequestHistory;
import com.example.currency.gateway.domain.EuroRates;
import com.example.currency.gateway.domain.JsonRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(ExternalServiceOne.class)
class ExternalServiceOneTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExternalServiceOne externalServiceOne;

    @Test
    void createRequestAndGetEURRates() throws Exception {
        JsonRequestCurrent jsonRequestCurrent = new JsonRequestCurrent(UUID.randomUUID(), Timestamp.valueOf("2021-01-31 00:00:00.000"),Long.valueOf(1234),"EUR");
        EuroRates currentEuroRates = new EuroRates(UUID.randomUUID(), Instant.now(), BigDecimal.valueOf(1.21),
                BigDecimal.valueOf(0.89), BigDecimal.valueOf(1.96));
        Mockito.doReturn(ResponseEntity.ok(currentEuroRates)).when(externalServiceOne).createRequestAndGetEURRates(jsonRequestCurrent);
    }

    @Test
    void createRequestAndGetHistoricalEURRates() {
        JsonRequestHistory jsonRequestHistory = new JsonRequestHistory(UUID.randomUUID(), Timestamp.valueOf("2021-01-31 00:00:00.000"),Long.valueOf(1234),"EUR", 24);
        EuroRates euroRates = new EuroRates(UUID.randomUUID(), Instant.now(), BigDecimal.valueOf(1.21),
                BigDecimal.valueOf(0.89), BigDecimal.valueOf(1.96));
        List<EuroRates> euroRatesList = List.of(euroRates);
        Mockito.doReturn(ResponseEntity.ok(euroRatesList)).when(externalServiceOne).createRequestAndGetHistoricalEURRates(jsonRequestHistory);
    }
}