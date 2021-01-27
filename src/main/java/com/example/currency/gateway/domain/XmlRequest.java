package com.example.currency.gateway.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "xmlRequests")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class XmlRequest implements Serializable {

    @Id
    String requestId;

    @Column(name = "consumer")
    String consumer;

    @Column(name = "currency")
    String currency;

}
