package com.example.couponcore.service;


import com.example.couponcore.exception.CouponIssueException;
import com.example.couponcore.repository.redis.RedisRepository;
import com.example.couponcore.repository.redis.dto.CouponRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.couponcore.repository.mysql.UserJpaRepository; // v3.2 validation userIdExists

import static com.example.couponcore.exception.ErrorCode.INVALID_USER_ID;

@RequiredArgsConstructor
@Service
public class AsyncCouponIssueServiceV2 {    // v2.3 Async coupon-api(Script, /v2/issue-async)

    private final RedisRepository redisRepository;
    private final CouponCacheService couponCacheService;
    private final UserJpaRepository userJpaRepository; // v3.2 validation userIdExists

    public void issue(long couponId, long userId) {
        validateUserIdExists(userId);                  // v3.2 validation userIdExists
        CouponRedisEntity coupon = couponCacheService.getCouponLocalCache(couponId);
        coupon.checkIssuableCoupon();
        issueRequest(couponId, userId, coupon.totalQuantity());
    }

    public void issueRequest(long couponId, long userCouponId, Integer totalIssueQuantity) {
        if (totalIssueQuantity == null) {
            redisRepository.issueRequest(couponId, userCouponId, Integer.MAX_VALUE);
        }
        redisRepository.issueRequest(couponId, userCouponId, totalIssueQuantity);
    }

    private void validateUserIdExists(long userId) {            // v3.2 validation userIdExists
        boolean exists = userJpaRepository.existsById(userId); // userRepository를 통해 존재 여부 확인
        if (!exists) {
            throw new CouponIssueException(INVALID_USER_ID, "존재하지 않는 사용자 ID입니다: %d".formatted(userId));
        }
    }
}
