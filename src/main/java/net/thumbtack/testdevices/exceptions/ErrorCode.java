package net.thumbtack.testdevices.exceptions;

public enum ErrorCode {
    USER_NOT_FOUND("email", "invalid email"),
    WRONG_PASSWORD("password", "password mismatch")
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
