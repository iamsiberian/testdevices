package net.thumbtack.testdevices.exceptions;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Error {
    private String errorCode;
    private String field;
    private String message;

    @JsonCreator
    public Error(
            final String errorCode,
            final String field,
            final String message
    ) {
        this.errorCode = errorCode;
        this.field = field;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}

