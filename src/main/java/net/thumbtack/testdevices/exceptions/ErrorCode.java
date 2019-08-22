package net.thumbtack.testdevices.exceptions;

public enum ErrorCode {
    USER_NOT_FOUND("email", "invalid email"),
    WRONG_PASSWORD("password", "password mismatch"),
    INVALID_AUTHORITY("authority", "authority not found"),
    DUPLICATE_KEY_EXCEPTION("", ""),
    DEVICE_IS_ALREADY_TAKEN_BY_YOU("", "the device is already taken by you"),
    DEVICE_IS_ALREADY_TAKEN_BUT_NOT_BY_YOU("", "the device is already taken but not by you"),
    DEVICE_IS_ALREADY_RETURN_BY_YOU("", "the device is already return by you"),

    ;

    private String field;
    private String message;

    ErrorCode(
            final String field,
            final String message
    ) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
