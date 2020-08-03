package com.ohdocha.admin.exception;

public class CustomException extends KnownException {

    public CustomException(int errorCode, String errorMessage) {
        super(1, errorCode, errorMessage);
    }

}
