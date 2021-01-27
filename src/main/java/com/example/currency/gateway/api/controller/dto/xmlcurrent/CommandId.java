package com.example.currency.gateway.api.controller.dto.xmlcurrent;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class CommandId {

    @JacksonXmlProperty(isAttribute = true)
    String id;

    @JacksonXmlProperty(localName = "get")
    GetField field;
}
