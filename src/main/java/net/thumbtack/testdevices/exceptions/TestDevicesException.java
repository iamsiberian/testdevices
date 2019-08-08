package net.thumbtack.testdevices.exceptions;

import java.util.ArrayList;
import java.util.List;

public class TestDevicesException extends RuntimeException {
    private List<ErrorCode> errorCode;

    public TestDevicesException() {
        super();
    }

    public TestDevicesException(final String message) {
        super(message);
    }

    public TestDevicesException(final ErrorCode errorCode) {
        this.errorCode = new ArrayList<>();
        this.errorCode.add(errorCode);
    }

    public TestDevicesException(final List<ErrorCode> errorCodes) {
        this.errorCode = errorCodes;
    }

    public List<ErrorCode> getErrorCode() {
        return errorCode;
    }
}
