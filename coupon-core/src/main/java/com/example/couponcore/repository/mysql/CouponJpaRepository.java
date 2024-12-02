package com.example.couponcore.repository.mysql;

import com.example.couponcore.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
}

/*
v0.2.2 CouponIssue Validation - CouponIssue 트랜잭션 기능
- CouponJpaRepository 클래스:
 */

