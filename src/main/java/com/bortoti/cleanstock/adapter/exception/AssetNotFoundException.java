package com.bortoti.cleanstock.adapter.exception;

public class AssetNotFoundException extends Exception {
    public AssetNotFoundException(){}

    public AssetNotFoundException(String message) {
        super(message);
    }

    public AssetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}
