package com.example.currency.gateway.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement
public class XmlRequests implements Serializable {

    private static final long serialVersionUID = 22L;

    @JacksonXmlProperty(localName = "XmlRequest")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<XmlRequest> xmlRequests = new ArrayList<>();

    public List<XmlRequest> getXmlRequests() {
        return xmlRequests;
    }

    public void setXmlRequests(List<XmlRequest> xmlRequests) {
        this.xmlRequests = xmlRequests;
    }
}