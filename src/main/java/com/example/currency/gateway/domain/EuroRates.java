package com.example.currency.gateway.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "eurorates")
@Data
@RequiredArgsConstructor
public class EuroRates {

    @Id
    @Type(type = "uuid-char")
    @NonNull
    UUID id;

    @Column(name = "time")
    @NonNull
    Instant timestamp;

    @Column(name = "GBP")
    @NonNull
    BigDecimal gbpRate;

    @Column(name = "USD")
    @NonNull
    BigDecimal usdRate;

    @Column(name = "AUD")
    @NonNull
    BigDecimal audRate;

    @Column(name = "NZD")
    @NonNull
    BigDecimal nzdRate;

    @Column(name = "CAD")
    @NonNull
    BigDecimal cadRate;

    @Column(name = "JPY")
    @NonNull
    BigDecimal jpyRate;

    @Column(name = "CHF")
    @NonNull
    BigDecimal chfRate;

    public EuroRates(Instant timestamp, @NonNull BigDecimal gbpRate, @NonNull BigDecimal usdRate, @NonNull BigDecimal audRate, @NonNull BigDecimal nzdRate, @NonNull BigDecimal cadRate, @NonNull BigDecimal jpyRate, @NonNull BigDecimal chfRate) {
        this.id = UUID.randomUUID();
        this.timestamp = timestamp;
        this.gbpRate = gbpRate;
        this.usdRate = usdRate;
        this.audRate = audRate;
        this.nzdRate = nzdRate;
        this.cadRate = cadRate;
        this.jpyRate = jpyRate;
        this.chfRate = chfRate;
    }

    public EuroRates(){

    }
}
