package com.example.couponcore;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.config.name=application-core")
@SpringBootTest(classes = CouponCoreConfiguration.class)
public class TestConfig {
}

/*
v0.2.2 CouponIssue Validation - CouponIssue 트랜잭션 기능
- application-core.yml test 환경설정 연결하기 위한 클래스
 */
