package com.example.currency.gateway.api.controller.dto.xmlhistory;

import com.example.currency.gateway.api.controller.dto.xmlcurrent.GetField;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class HistoryCommandId {

    @JacksonXmlProperty(isAttribute = true)
    String id;

    @JacksonXmlProperty(localName = "get")
    History history;
}
