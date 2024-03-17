package com.springkafkaexample.demo.exception.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    INTERNAL_SERVER_ERROR("91", "Internal server error"),
    REQUEST_NOT_VALID("02", "Request not valid");

    final String code;
    final String message;

    @JsonCreator
    public static ErrorCodeEnum valueForCode(String code) {
        return Arrays.stream(ErrorCodeEnum.values())
                .filter(errorCodeEnum -> errorCodeEnum.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }
}
