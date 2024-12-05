package com.example.couponapi;

import com.example.couponapi.controller.dto.CouponIssueRequestDto;
import com.example.couponapi.controller.dto.CouponIssueResponseDto;
import com.example.couponapi.service.CouponIssueRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CouponIssueController {

    private final CouponIssueRequestService couponIssueRequestService;

    @PostMapping("/v1/issue")
    public CouponIssueResponseDto issueV1(@RequestBody CouponIssueRequestDto body) {
        couponIssueRequestService.issueRequestV1(body);
        return new CouponIssueResponseDto(true, null);
    }

    // v2.1. Async :coupon-api(set, /v1/issue-async)
    @PostMapping("/v1/issue-async")
    public CouponIssueResponseDto issueAsyncV1(@RequestBody CouponIssueRequestDto body) {
        couponIssueRequestService.asyncIssueRequestV1(body);
        return new CouponIssueResponseDto(true, null);
    }

    // v2.3.0 Async coupon-api(Script, /v2/issue-async)
    @PostMapping("/v2/issue-async")
    public CouponIssueResponseDto issueAsyncV2(@RequestBody CouponIssueRequestDto body) {
        couponIssueRequestService.asyncIssueRequestV2(body);
        return new CouponIssueResponseDto(true, null);
    }
}

/*
v1.0.0	coupon-api (BASECODE) issueRequestV1 - ("/v1/issue")
- HTTP 요청을 처리하는 컨트롤러 클래스
- RESTful API 엔드 포인트를 제공하여 쿠폰 발급 요청을 처리
- 서비스 호출 후, 응답객체(CouponIssueResponseDto)를 반환
 */