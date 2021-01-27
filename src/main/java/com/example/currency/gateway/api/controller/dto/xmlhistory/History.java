package com.example.currency.gateway.api.controller.dto.xmlhistory;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class History {

    @JacksonXmlProperty(isAttribute = true)
    String consumer;

    @JacksonXmlProperty
    String currency;

    @JacksonXmlProperty
    Integer period;
}
