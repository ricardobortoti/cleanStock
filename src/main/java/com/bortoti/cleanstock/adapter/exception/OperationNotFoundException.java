package com.bortoti.cleanstock.adapter.exception;

public class OperationNotFoundException extends Exception {
    public OperationNotFoundException(){}

    public OperationNotFoundException(String message) {
        super(message);
    }
}
