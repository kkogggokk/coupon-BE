package com.example.couponapi.service;

import com.example.couponapi.controller.dto.CouponIssueRequestDto;
import com.example.couponcore.exception.CouponIssueException;
import com.example.couponcore.model.Coupon;
import com.example.couponcore.model.CouponIssue;
import com.example.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.example.couponcore.repository.mysql.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


import static com.example.couponcore.exception.ErrorCode.COUPON_NOT_EXIST;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponRegisterService {    // v3.0 kmong

    private final CouponJpaRepository couponJpaRepository;
    private final CouponIssueJpaRepository couponIssueJpaRepository;

    @Transactional
    public CouponIssue register(final CouponIssueRequestDto body) {
        log.info("body[{}]", body);

        final Coupon coupon = couponJpaRepository.findCouponWithLock(body.couponId()).orElseThrow(
                () -> new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(body.couponId())));
        log.info("coupon[{}]", coupon);

        final Long TEMP_USER_ID = 0L;

        // 쿠폰 등록
        final CouponIssue couponIssue = new CouponIssue();
        couponIssue.setCouponId(coupon.getId());
        couponIssue.setUserId(TEMP_USER_ID);
        couponIssue.setDateIssued(LocalDateTime.now());
        couponIssueJpaRepository.save(couponIssue);

        // 쿠폰 발급량 증가
        coupon.issue();

        return couponIssue;
    }
}
