package net.thumbtack.testdevices.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import net.thumbtack.testdevices.exceptions.Error;

import java.util.List;

public class ErrorsResponse {
    private List<Error> errorList;

    @JsonCreator
    public ErrorsResponse(final List<Error> errorList) {
        this.errorList = errorList;
    }

    public List<Error> getErrorList() {
        return errorList;
    }
}
