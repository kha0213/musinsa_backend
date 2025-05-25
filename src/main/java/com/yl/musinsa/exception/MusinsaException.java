package com.yl.musinsa.exception;

import lombok.Getter;

@Getter
public class MusinsaException extends RuntimeException {
    private final ErrorCode code;
    private final String message;

    private MusinsaException(ErrorCode errorCode, String args) {
        super(errorCode.getMessage(args));
        this.code = errorCode;
        this.message = errorCode.getMessage(args);
    }

    public static MusinsaException duplicateName(String name) {
        return new MusinsaException(ErrorCode.DUPLICATE_NAME, name);
    }
}
