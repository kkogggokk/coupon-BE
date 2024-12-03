package com.example.couponcore.repository.mysql;

import com.example.couponcore.model.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
}

/*
v0.2.2 CouponIssue Validation - CouponIssue 트랜잭션 기능
- CouponJpaRepository 클래스:
 */

