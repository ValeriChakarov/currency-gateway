package com.example.currency.gateway.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "jsonRequests")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class JsonRequest {

    @Id
    @Type(type = "uuid-char")
    @NonNull
    UUID requestId;

    @Column(name = "time", nullable = false)
    @NonNull
    Timestamp timestamp;

    @Column(name = "client_id", nullable = false)
    @NonNull
    Long clientId;

    @Column(name = "client_currency", nullable = false)
    @NonNull
    String clientCurrency;

}
