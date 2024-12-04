package com.example.couponcore.service;

import com.example.couponcore.component.DistributeLockExecutor;
import com.example.couponcore.exception.CouponIssueException;
import com.example.couponcore.model.Coupon;
import com.example.couponcore.repository.redis.RedisRepository;
import com.example.couponcore.repository.redis.dto.CouponIssueRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.couponcore.exception.ErrorCode.*;
import static com.example.couponcore.util.CouponRedisUtils.getIssueRequestKey;
import static com.example.couponcore.util.CouponRedisUtils.getIssueRequestQueueKey;

@RequiredArgsConstructor
@Service
public class AsyncCouponIssueServiceV1 {

    private final RedisRepository redisRepository;
    private final CouponIssueRedisService couponIssueRedisService;
    private final DistributeLockExecutor distributeLockExecutor;
//    private final CouponCacheService couponCacheService;  // v2.1.1 Async :coupon-api(Cache, /v1/issue-async)
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CouponIssueService couponIssueService;

    public void issue(long couponId, long userId) {
        // 1차 코드
        Coupon coupon = couponIssueService.findCoupon(couponId);
        if(!coupon.availableIssueDate()){
            throw new CouponIssueException(INVALID_COUPON_ISSUE_DATE, "발급 가능한 일자가 아닙니다. couponId: %s, issueStart: %s, issueEnd: %s".formatted(couponId, coupon.getDateIssueStart(), coupon.getDateIssueEnd()));
        }
        if(!couponIssueRedisService.availableTotalIssueQuantity(coupon.getTotalQuantity(), couponId)){
            throw new CouponIssueException(INVALID_COUPON_ISSUE_QUANTITY, "발급 가능한 수량을 초과합니다. couponId: %s, UserId: %s".formatted(couponId, userId));
        }
        if(!couponIssueRedisService.availableUserIssueQuantity(couponId, userId)){
            throw new CouponIssueException(DUPLICATED_COUPON_ISSUE, "이미 발급 요청이 처리됐습니다. couponId: %s, UserId: %s".formatted(couponId, userId));
        }
        issueRequest(couponId, userId);
    }

    private void issueRequest(long couponId, long userId) { // 쿠폰 발급 트랜잭션
        CouponIssueRequest issueRequest = new CouponIssueRequest(couponId, userId);
        try {
            String value = objectMapper.writeValueAsString(issueRequest);
            redisRepository.sAdd(getIssueRequestKey(couponId), String.valueOf(userId));
            redisRepository.rPush(getIssueRequestQueueKey(), value);
        } catch (JsonProcessingException e) {
            throw new CouponIssueException(FAIL_COUPON_ISSUE_REQUEST, "input: %s".formatted(issueRequest));
        }
    }
}
