package com.example.currency.gateway.api.controller.dto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.sql.Timestamp;
import java.util.UUID;

@Value
@RequiredArgsConstructor
public class JsonRequestHistory {

    @NonNull
    UUID requestId;

    @NonNull
    Timestamp timestamp;

    @NonNull
    Long client;

    @NonNull
    String currency;

    @NonNull
    Integer period;
}
