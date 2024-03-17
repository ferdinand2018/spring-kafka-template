package com.springkafkaexample.demo.exception;

import com.springkafkaexample.demo.exception.error.ErrorCodeEnum;
import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageValidationException extends BaseApplicationException {

    public MessageValidationException(String details) {
        super(ErrorCodeEnum.REQUEST_NOT_VALID, buildValidationErrorMessage(details));
    }

    public <T> MessageValidationException(Set<ConstraintViolation<T>> constraintViolations) {
        super(ErrorCodeEnum.REQUEST_NOT_VALID, buildValidationErrorMessage(constraintViolations));
    }

    private static <T> String buildValidationErrorMessage(Set<ConstraintViolation<T>> constraintViolations) {
        return buildValidationErrorMessage(constraintViolations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; ")));
    }

    private static String buildValidationErrorMessage(String details) {
        return "Message did not pass validation: {%s}, skip and commit".formatted(details);
    }
}
