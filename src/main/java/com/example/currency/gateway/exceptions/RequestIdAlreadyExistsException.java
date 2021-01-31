package com.example.currency.gateway.exceptions;

public class RequestIdAlreadyExistsException extends RuntimeException {

    String message;

    public RequestIdAlreadyExistsException(String message){
        super(message);
    }
}
