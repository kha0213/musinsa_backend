package com.yl.musinsa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 BAD_REQUEST
    BAD_REQUEST_BODY(HttpStatus.BAD_REQUEST, "유효하지 않은 값입니다."),

    // 409 CONFLICT
    DUPLICATE_NAME(HttpStatus.CONFLICT, "이미 등록된 이름입니다. [%s]"),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String messageTemplate;

    public String getMessage(Object... args) {
        return String.format(messageTemplate, args);
    }
}
