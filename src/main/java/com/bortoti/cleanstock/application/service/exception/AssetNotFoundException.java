package com.bortoti.cleanstock.application.service.exception;

public class AssetNotFoundException extends Exception {
    public AssetNotFoundException(){}

    public AssetNotFoundException(String message) {
        super(message);
    }
}
