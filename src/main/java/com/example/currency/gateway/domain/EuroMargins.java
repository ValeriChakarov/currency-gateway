package com.example.currency.gateway.domain;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "euro_six_months_margins")
@Data
public class EuroMargins {

    @Id
    @Type(type = "uuid-char")
    @NonNull
    UUID id;

    @Column(name = "date_calculated", nullable = false)
    @NonNull
    LocalDate date;

    @Column(name = "usd_margin")
    @NonNull
    BigDecimal usdmargin;

    @Column(name = "gbp_margin")
    @NonNull
    BigDecimal gbpmargin;

    @Column(name = "bgn_margin")
    @NonNull
    BigDecimal bgnmargin;

    public EuroMargins(@NonNull LocalDate date, @NonNull BigDecimal usdmargin, @NonNull BigDecimal gbpmargin, @NonNull BigDecimal bgnmargin) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.usdmargin = usdmargin;
        this.gbpmargin = gbpmargin;
        this.bgnmargin = bgnmargin;
    }

    public EuroMargins() {
    }
}
