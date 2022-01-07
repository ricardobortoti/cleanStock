package com.bortoti.cleanstock.application.service.exception;

public class BrokerageNotFoundException extends Exception{
    public BrokerageNotFoundException(String message) {
        super(message);
    }
}
