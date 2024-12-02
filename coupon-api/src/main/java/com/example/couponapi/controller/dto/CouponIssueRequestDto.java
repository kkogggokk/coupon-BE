package com.example.couponapi.controller.dto;

public record CouponIssueRequestDto(long userId, long couponId) {
}


/*
v1.0.0	coupon-api (BASECODE) issueRequestV1 - ("/v1/issue")
- 쿠폰 발급 요청을 위한 데이터 전송 객체(DTO)
- userId와 couponId를 포함한 요청 정보를 전달
- Java Record 활용하여 불변 객체로 정의
 */