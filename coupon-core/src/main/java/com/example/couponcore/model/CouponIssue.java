package com.example.couponcore.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coupon_issues")
public class CouponIssue extends BaseTimeEntity {

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

    @PrePersist
    public void prePersist() {  // @PrePersist 메서드를 추가하여 dateIssued가 null일 경우 자동으로 현재 시간을 설정
        if (dateIssued == null) {
            dateIssued = LocalDateTime.now();
        }
    }

    private LocalDateTime dateUsed;
}


/*
v0.2.0 MySQL Entity setting
- coupon_issues 테이블의 엔티티 생성 @Table(name="coupon_issues")
- 공통적으로 사용하는 BaseTimeEntity를 상속받음

v0.2.2 CouponIssue Validation - CouponIssue 트랜잭션 기능
- @PrePersist 메서드를 추가하여 dateIssued가 null일 경우 자동으로 현재 시간을 설정

*/
