package com.example.couponcore.util;

public class CouponRedisUtils {

    public static String getIssueRequestKey(long couponId) {
        return "issue.request.couponId=%s".formatted(couponId);
    }

    public static String getIssueRequestQueueKey() {
        return "issue.request";
    }
}


/*
v2.1.1 Async :coupon-api(set, /v1/issue-async)
- redis 관리를 위한 유틸리티
- couponId를 Redis 키로 사용
 */