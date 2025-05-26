package com.yl.musinsa.exception;

import lombok.Getter;

@Getter
public class MusinsaException extends RuntimeException {
    private final ErrorCode code;
    private final String message;

    public MusinsaException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode;
        this.message = errorCode.getMessage();
    }
}
