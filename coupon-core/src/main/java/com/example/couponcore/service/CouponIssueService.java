package com.example.couponcore.service;

import com.example.couponcore.exception.CouponIssueException;
import com.example.couponcore.model.Coupon;
import com.example.couponcore.model.CouponIssue;
import com.example.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.example.couponcore.repository.mysql.CouponIssueRepository;
import com.example.couponcore.repository.mysql.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.couponcore.exception.ErrorCode.COUPON_NOT_EXIST;
import static com.example.couponcore.exception.ErrorCode.DUPLICATED_COUPON_ISSUE;

@RequiredArgsConstructor
@Service
public class CouponIssueService {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponIssueJpaRepository couponIssueJpaRepository;
    private final CouponIssueRepository couponIssueRepository;

    @Transactional
    public void issue(long couponId, long userId) { //사용자(UserId)의 쿠폰 발급 트랜잭션 로직
        Coupon coupon = findCoupon(couponId);       //쿠폰 조회
        coupon.issue();                             //쿠폰 발급 처리(issuedQuantity++ 내부 상태 변경)
        saveCouponIssue(couponId, userId);          //쿠폰 발급 성공 대상을 DB에 저장
    }

    @Transactional(readOnly = true)
    public Coupon findCoupon(long couponId) {       //쿠폰 조회
        return couponJpaRepository.findById(couponId).orElseThrow(() -> {   //쿠폰(couponId) 조회, 쿠폰이 없을 경우 예외발생
            throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }

    @Transactional
    public CouponIssue saveCouponIssue(long couponId, long userId) {        //쿠폰 발급 성공 대상을 DB에 저장
        checkAlreadyIssuance(couponId, userId);                             //중복 발급 체크
        CouponIssue couponIssue = CouponIssue.builder()                           //발급 객체 생성
                .couponId(couponId)
                .userId(userId)
                .build();
        return couponIssueJpaRepository.save(couponIssue);                        //DB 저장
    }

    private void checkAlreadyIssuance(long couponId, long userId) {         //중복 발급 체크
        CouponIssue issue = couponIssueRepository.findFirstCouponIssue(couponId, userId); // 발급 여부 확인
        if (issue != null) {
            throw new CouponIssueException(DUPLICATED_COUPON_ISSUE, "이미 발급된 쿠폰입니다. user_id: %d, coupon_id: %d".formatted(userId, couponId));
        }
    }
}


/*
v0.2.2 CouponIssue Validation - CouponIssue 트랜잭션 기능
- CouponIssueService 클래스: 쿠폰 발급 관련 비즈니스 로직 처리
-- issue(): 사용자(UserId)의 쿠폰 발급 트랜잭션 로직
-- findCoupon(): 쿠폰 조회 메서드
-- saveCouponIssue(): 쿠폰 발급 성공 대상을 DB에 저장
-- checkAlreadyIssuance(): 중복 발급 체크
*/