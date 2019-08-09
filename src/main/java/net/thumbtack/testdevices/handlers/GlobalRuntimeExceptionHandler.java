package net.thumbtack.testdevices.handlers;

import net.thumbtack.testdevices.core.repositories.DaoException;
import net.thumbtack.testdevices.dto.response.ErrorsResponse;
import net.thumbtack.testdevices.exceptions.Error;
import net.thumbtack.testdevices.exceptions.ErrorCode;
import net.thumbtack.testdevices.exceptions.TestDevicesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalRuntimeExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRuntimeExceptionHandler.class);
    private static final int DUPLICATE_ERROR_FIELD = 5;
    private static final String EMPTY_ERROR_FIELD = "";
    private static final String DEFAULT_VALIDATE_ERROR_CAUSE = "_IS_INVALID";

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity handleSQLIntegrityConstraintViolationException(final SQLIntegrityConstraintViolationException ex) {
        LOGGER.error("Handle SQLIntegrityConstraintViolationException {}", ex);
        List<Error> errorList = new ArrayList<>();
        String errorCode = ex.getClass().getSimpleName().toUpperCase();
        String[] errorMessage = ex.getMessage().split(" ");
        errorList.add(
                new Error(
                        errorCode,
                        errorMessage[DUPLICATE_ERROR_FIELD].replace("'", ""),
                        ex.getMessage()
                )
        );
        return ResponseEntity.badRequest().body(new ErrorsResponse(errorList));
    }

    @ExceptionHandler(DaoException.class)
    public ResponseEntity handleDaoException(final DaoException ex) {
        LOGGER.error("Handle DaoException {}", ex);
        List<Error> errorList = new ArrayList<>();
        errorList.add(
                new Error(
                        ex.toString(),
                        EMPTY_ERROR_FIELD,
                        ex.getMessage()
                )
        );
        return ResponseEntity.badRequest().body(new ErrorsResponse(errorList));
    }

    @ExceptionHandler(TestDevicesException.class)
    public ResponseEntity handleTestDevicesException(final TestDevicesException ex) {
        LOGGER.error("Handle TestDevicesException {}", ex);
        List<Error> errorList = new ArrayList<>();
        List<ErrorCode> errorCodes = ex.getErrorCode();
        for (ErrorCode errorCode : errorCodes) {
            errorList.add(new Error(
                    errorCode.name(),
                    errorCode.getField(),
                    errorCode.getMessage()
            ));
        }
        return ResponseEntity.badRequest().body(new ErrorsResponse(errorList));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        LOGGER.error("Handle MethodArgumentNotValidException {}", ex);
        List<Error> errorList = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errorList.add(
                    new Error(
                            fieldError.getField().toUpperCase() + DEFAULT_VALIDATE_ERROR_CAUSE,
                            fieldError.getField(),
                            fieldError.getDefaultMessage()
                    )
            );
        }
        return ResponseEntity.badRequest().body(new ErrorsResponse(errorList));
    }
}
