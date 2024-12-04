package com.example.couponcore.repository.redis.dto;

public record CouponIssueRequest(long couponId, long userId) {
}
//v2.1.1 Async :coupon-api(set, /v1/issue-async)