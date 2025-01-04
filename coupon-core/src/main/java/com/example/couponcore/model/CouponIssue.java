package com.example.couponcore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "coupon_issues")
public class CouponIssue extends BaseTimeEntity {
    // v0.2.0 MySQL Entity setting - coupon_issues 테이블의 엔티티 생성 @Table(name="coupon_issues")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime dateIssued;

    private LocalDateTime dateUsed;
}