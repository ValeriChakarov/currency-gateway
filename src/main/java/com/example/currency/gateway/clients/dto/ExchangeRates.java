package com.example.currency.gateway.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ExchangeRates {

    boolean success;

    Timestamp timestamp;

    String base;

    String date;

    private Map<String, BigDecimal> rates;

}
