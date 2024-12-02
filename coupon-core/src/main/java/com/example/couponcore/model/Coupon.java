package com.example.couponcore.model;

import com.example.couponcore.exception.CouponIssueException;
import com.example.couponcore.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

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

    // v0.2.1 쿠폰 검증기능(coupon validataion)
    public boolean availableIssueQuantity(){    //쿠폰 발급 수량 검증: availableIssueQuantity()
        if(totalQuantity == null){              //totalQuntity null 허용하므로 true 반환
            return true;
        }
        return totalQuantity > issuedQuantity;  // 쿠폰 발급 가능(true), 쿠폰 발급 실패(false)
    }

    public boolean availableIssueDate(){        // 이벤트 기한 검증: availableIssueDate()
        LocalDateTime now = LocalDateTime.now();
        return dateIssueStart.isBefore(now) && dateIssueEnd.isAfter(now);
    }

    public boolean isIssueComplete() {
        LocalDateTime now = LocalDateTime.now();
        return dateIssueEnd.isBefore(now) || !availableIssueQuantity();
    }

    public void issue(){
        // 수량검증, 기간검증
        if(!availableIssueQuantity()){
//            throw new RuntimeException("수량검증");
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY, "발급 가능한 수량을 초과합니다. total : %s, issued: %s".formatted(totalQuantity, issuedQuantity));
        }
        if(!availableIssueDate()){
//            throw new RuntimeException("기간검증");
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_DATE, "발급 가능한 일자가 아닙니다. requestDate: %s, issueStart: %s, issueEnd: %s".formatted(LocalDateTime.now(), dateIssueStart, dateIssueEnd));
        }
        issuedQuantity++;
    }
}

/*
v0.2.0 MySQL Entity setting
- coupon_issues 테이블의 엔티티 생성 @Table(name="coupon_issues")
- 공통적으로 사용하는 BaseTimeEntity를 상속받음

v0.2.1 쿠폰 검증 기능(coupon validataion)
- 쿠폰 발급 수량 검증: availableIssueQuantity()
- 이벤트 기간 검증: availableIssueDate()
- 쿠폰 발행 여부 검증: isIssueComplete()
- 쿠폰 발급: issue()
 */

