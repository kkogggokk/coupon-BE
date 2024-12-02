package com.example.couponcore.repository.mysql;

import com.example.couponcore.model.CouponIssue;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.couponcore.model.QCouponIssue.couponIssue;

@RequiredArgsConstructor
@Repository
public class CouponIssueRepository {

    private final JPQLQueryFactory queryFactory;

    public CouponIssue findFirstCouponIssue(long couponId, long userId) {
        return queryFactory.selectFrom(couponIssue)
                .where(couponIssue.couponId.eq(couponId))
                .where(couponIssue.userId.eq(userId))
                .fetchFirst(); //couponId, UserID 일치하는 첫번째 결과를 반환
    }
}

/*
v0.2.2 CouponIssue Validation - CouponIssue 트랜잭션 기능
- CouponIssueRepository 클래스: QueryDSL을 활용하여 복잡한 쿼리를 동적으로 작성하는 레포지토리
- JPQLQueryFactory를 사용하여 직접 SQL-like JPQL 쿼리 작성
- findFirstCouponIssue(): 사용자의 쿠폰 중복 발급 여부 확인

Q. 이건 왜 따로 클래스를 만든거지?
*/
