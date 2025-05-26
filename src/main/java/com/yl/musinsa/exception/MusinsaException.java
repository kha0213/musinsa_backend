package com.yl.musinsa.exception;

import lombok.Getter;

@Getter
public class MusinsaException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public MusinsaException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
