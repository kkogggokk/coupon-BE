package com.example.couponcore.service;

import com.example.couponcore.exception.CouponIssueException;
import com.example.couponcore.model.Coupon;
import com.example.couponcore.model.CouponIssue;
//import com.example.couponcore.model.event.CouponIssueCompleteEvent;   // v3
import com.example.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.example.couponcore.repository.mysql.CouponIssueRepository;
import com.example.couponcore.repository.mysql.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.couponcore.exception.ErrorCode.COUPON_NOT_EXIST;
import static com.example.couponcore.exception.ErrorCode.DUPLICATED_COUPON_ISSUE;

@RequiredArgsConstructor
@Service
public class CouponIssueService {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponIssueJpaRepository couponIssueJpaRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void issue(long couponId, long userId) {
        Coupon coupon = findCouponWithLock(couponId);   // CHECK - findCoupon, findCouponWithLock
        coupon.issue();
        saveCouponIssue(couponId, userId);
//        publishCouponEvent(coupon);
    }

    // v1.1 ~ v1.2
    @Transactional(readOnly = true)
    public Coupon findCoupon(long couponId) { // MySQL Lock 이전
        return couponJpaRepository.findById(couponId).orElseThrow(() -> {
            throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }

    // v1.3.0 coupon-api(MySQL Lock)
    @Transactional
    public Coupon findCouponWithLock(long couponId) {
        return couponJpaRepository.findCouponWithLock(couponId).orElseThrow(() -> {
            throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }

    @Transactional
    public CouponIssue saveCouponIssue(long couponId, long userId) {
        checkAlreadyIssuance(couponId, userId);
        CouponIssue couponIssue = CouponIssue.builder()
                .couponId(couponId)
                .userId(userId)
                .build();
        return couponIssueJpaRepository.save(couponIssue);
    }

    private void checkAlreadyIssuance(long couponId, long userId) {
        CouponIssue issue = couponIssueRepository.findFirstCouponIssue(couponId, userId);
        if (issue != null) {
            throw new CouponIssueException(DUPLICATED_COUPON_ISSUE, "이미 발급된 쿠폰입니다. user_id: %d, coupon_id: %d".formatted(userId, couponId));
        }
    }

//    private void publishCouponEvent(Coupon coupon) {  // v3
//        if (coupon.isIssueComplete()) {
//            applicationEventPublisher.publishEvent(new CouponIssueCompleteEvent(coupon.getId()));
//        }
//    }
}


/*
v0.2.2 CouponIssue Validation - CouponIssue 트랜잭션 기능
- CouponIssueService 클래스: 쿠폰 발급 관련 비즈니스 로직 처리
-- issue(): 사용자(UserId)의 쿠폰 발급 트랜잭션 로직
-- findCoupon(): 쿠폰 조회 메서드
-- saveCouponIssue(): 쿠폰 발급 성공 대상을 DB에 저장
-- checkAlreadyIssuance(): 중복 발급 체크
*/