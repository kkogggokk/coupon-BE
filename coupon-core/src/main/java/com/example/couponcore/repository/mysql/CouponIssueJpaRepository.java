package com.example.couponcore.repository.mysql;

import com.example.couponcore.model.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponIssueJpaRepository extends JpaRepository<CouponIssue, Long> {
}


/*
v0.2.2 CouponIssue Validation - CouponIssue 트랜잭션 기능
- CouponIssueJpaRepository 클래스: JpaRepository를 상속받아 쿠폰발급(CouponIssue) 엔티티에 대한 기본 CRUD 작업 제공
 */