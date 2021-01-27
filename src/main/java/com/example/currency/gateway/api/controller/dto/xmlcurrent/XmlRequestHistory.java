package com.example.currency.gateway.api.controller.dto.xmlcurrent;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class XmlRequestHistory {

    Long id;

    Long consumer;

    String currency;

    Integer period;
}
