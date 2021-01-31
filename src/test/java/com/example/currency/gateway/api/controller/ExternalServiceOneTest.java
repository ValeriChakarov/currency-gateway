package com.example.currency.gateway.api.controller;

import com.example.currency.gateway.domain.JsonRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@WebMvcTest(ExternalServiceOne.class)
class ExternalServiceOneTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExternalServiceOne externalServiceOne;

    @Test
    void createRequestAndGetEURRates() {
//        JsonRequest jsonRequest = new JsonRequest(UUID.randomUUID(), Timestamp.valueOf("2021-01-30"), Long.valueOf("1234"), "EUR");
//        List<JsonRequest> jsonRequests = List.of(jsonRequest);
//        Mockito.doReturn(jsonRequests).when(externalServiceOne).get;
//
//        mvc.perform(MockMvcRequestBuilders.get("/api/v1/guest/allGuests")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is(guest.getFirstName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is(guest.getLastName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is(guest.getEmail())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber", Matchers.is(guest.getPhoneNumber())));
    }

    @Test
    void createRequestAndGetHistoricalEURRates() {
    }
}