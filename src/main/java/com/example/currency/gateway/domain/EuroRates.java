package com.example.currency.gateway.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "euro_rates")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = EuroRates.class)
public class EuroRates {

    @Id
    @Type(type = "uuid-char")
    @NonNull
    UUID id;

    @Column(name = "time")
    @NonNull
    Instant timestamp;

    @Column(name = "gbp")
    @NonNull
    BigDecimal gbpRate;

    @Column(name = "usd")
    @NonNull
    BigDecimal usdRate;

    @Column(name = "bgn")
    @NonNull
    BigDecimal bgnRate;
}