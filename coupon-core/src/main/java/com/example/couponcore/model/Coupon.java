package com.example.couponcore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "coupons")
public class Coupon extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CouponType couponTpe;

    private Integer totalQuantity;

    @Column(nullable = false)
    private  int issuedQuantity;

    @Column(nullable = false)
    private int minAvailableAmount;

    @Column(nullable = false)
    private LocalDateTime dateIssueStart;

    @Column(nullable = false)
    private LocalDateTime dateIssueEnd;
}

/*
coupon 테이블 엔티티 작성 : @Table(name = "coupons")
- 공통적으로 사용하는 BaseTimeEntity를 상속받음
- total_quantity는 Null이 가능하므로 Interger 타입으로 설정
 */