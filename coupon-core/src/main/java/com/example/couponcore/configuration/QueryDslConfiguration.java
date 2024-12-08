package com.example.couponcore.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class QueryDslConfiguration {

  @PersistenceContext
  private final EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }
}

/*
v0.2.2 CouponIssue Validation - CouponIssue 트랜잭션 기능
- EntityManager:
- JPAQueryFactory:
설정 후 Gredle 컴파일, Q클래스 있는지 확인.
*/