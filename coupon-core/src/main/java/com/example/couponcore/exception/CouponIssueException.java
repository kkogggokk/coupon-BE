package com.example.couponcore.exception;

import lombok.Getter;

@Getter
public class CouponIssueException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public CouponIssueException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "[%s] %s".formatted(errorCode, message);
    }
}

/*
예외처리를 따로 관리하기 위한 클래스
*/