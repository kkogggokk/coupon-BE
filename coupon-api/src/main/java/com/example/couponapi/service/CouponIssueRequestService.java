package com.example.couponapi.service;

import com.example.couponapi.controller.dto.CouponIssueRequestDto;
import com.example.couponcore.component.DistributeLockExecutor;
import com.example.couponcore.service.AsyncCouponIssueServiceV1;
//import com.example.couponcore.service.AsyncCouponIssueServiceV2;
import com.example.couponcore.component.DistributeLockExecutor;
import com.example.couponcore.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponIssueRequestService {

    private final CouponIssueService couponIssueService;
    private final AsyncCouponIssueServiceV1 asyncCouponIssueServiceV1;
//    private final AsyncCouponIssueServiceV2 asyncCouponIssueServiceV2;
    private final DistributeLockExecutor distributeLockExecutor;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());


    public void issueRequestV1(CouponIssueRequestDto requestDto) {
        /* -----v1.1.0 coupon-api(Synchronized)
        synchronized (this){
            couponIssueService.issue(requestDto.couponId(), requestDto.userId());
        }

        // v1.2.0 coupon-api(Redis Lock)
        distributeLockExecutor.execute ("lock_"+requestDto.couponId(), 1000,1000,() -> {
            couponIssueService.issue(requestDto.couponId(), requestDto.userId());
        });-----*/

        // v1.3.0 coupon-api(MySQL Lock)
        couponIssueService.issue(requestDto.couponId(), requestDto.userId());
        log.info("쿠폰 발급 완료. couponId: %s, userId: %s".formatted(requestDto.couponId(), requestDto.userId()));
    }

    // v2.1.1 Async :coupon-api(set, /v1/issue-async)
    public void asyncIssueRequestV1(CouponIssueRequestDto requestDto) {
        asyncCouponIssueServiceV1.issue(requestDto.couponId(), requestDto.userId());
    }

//    public void asyncIssueRequestV2(CouponIssueRequestDto requestDto) {
//        asyncCouponIssueServiceV2.issue(requestDto.couponId(), requestDto.userId());
//    }
}


/*
v1.0.0	coupon-api (BASECODE) issueRequestV1 - ("/v1/issue")
- 쿠폰 발급 요청을 처리하는 서비스
- 외부요청(CouponIssueRequestDto)을 받아 CouponIssueService를 호출하여 쿠폰 발급 로직을 실행
 */