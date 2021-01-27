package com.example.currency.gateway.api.controller.dto.xmlcurrent;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Currency {

    @JacksonXmlProperty(isAttribute = true, localName = "currency")
    String value;
}
