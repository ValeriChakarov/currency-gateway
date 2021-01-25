package com.example.currency.gateway.services;

public class RequestIdAlreadyExistsException extends RuntimeException {

    String message;

    public RequestIdAlreadyExistsException(String message){
        super(message);
    }
}
