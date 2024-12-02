package com.example.couponapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(value = NON_NULL)
public record CouponIssueResponseDto(boolean isSuccess, String comment) {
}

/*
v1.0.0	coupon-api (BASECODE) issueRequestV1 - ("/v1/issue")
- 쿠폰 발급 결과를 반환하는 데이터 전송객체(DTO)
- JSON 직렬화 시 comment 필드 값이 없으면 생략됩니다. (NON-NULL)
 */