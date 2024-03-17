package com.springkafkaexample.demo.exception;

import com.springkafkaexample.demo.exception.error.ErrorCodeEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class BaseApplicationException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "%s. Details: %s";

    private final ErrorCodeEnum errorCode;
    private final String details;

    public BaseApplicationException(ErrorCodeEnum errorCode, String details) {
        super(MESSAGE_TEMPLATE.formatted(errorCode.getMessage(), details));
        this.errorCode = errorCode;
        this.details = details;
    }

    public BaseApplicationException(ErrorCodeEnum errorCode, String details, Throwable cause) {
        super(MESSAGE_TEMPLATE.formatted(errorCode.getMessage(), details), cause);
        this.errorCode = errorCode;
        this.details = details;
    }

    public BaseApplicationException(ErrorCodeEnum errorCode, Throwable cause) {
        this(errorCode, StringUtils.EMPTY, cause);
    }

    public BaseApplicationException(ErrorCodeEnum errorCode) {
        this(errorCode, StringUtils.EMPTY);
    }
}
