package com.ohdocha.admin.exception;

import com.google.gson.Gson;
import lombok.Getter;

@Getter
public class RestKnownException extends RuntimeException {

    protected int httpStatusCode;
    protected int errorCode;
    protected String errorMessage;

    public RestKnownException(int httpStatusCode, int errorCode, String errorMessage) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
