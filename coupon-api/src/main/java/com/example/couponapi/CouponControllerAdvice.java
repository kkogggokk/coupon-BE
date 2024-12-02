package com.example.couponapi;

import com.example.couponapi.controller.dto.CouponIssueResponseDto;
import com.example.couponcore.exception.CouponIssueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponControllerAdvice {

    @ExceptionHandler(CouponIssueException.class)
    public CouponIssueResponseDto couponIssueExceptionHandler(CouponIssueException exception) {
        return new CouponIssueResponseDto(false, exception.getErrorCode().message);
    }
}

/*
v1.0.0	coupon-api (BASECODE) issueRequestV1 - ("/v1/issue")
- 전역 예외 처리 클래스
- CouponIssueException 발생 시, 표준화된 응답을 반환
- 클라리언트가 실패 원인을 이해할 수 있도록 CouponIssueResponseDto를 반환
 */