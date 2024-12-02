package com.example.couponcore.model;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateUpdated;
}

/*
coupon-, coupon-issue 테이블에 공통적으로 date_created, date_updated 필드 존재.
관리차원에서 필요한 필드로, 해당 부분 엔티티 생성
- @MappedSuperclass: 다른 엔티티 클래스의 부모 클래스가 될 수 있음을 지정, 상속받는 엔티티 클래스에서 공통으로 사용할 필드를 정의할 때 사용
- @EntityListeners(AuditingEntityListener.class): 주로 엔티티 생성, 수정 등의 이벤트를 감지하고, 생성일, 수정일과 같은 필드를 자동으로 업데이트 할 때 사용
- AuditingEntityListener는 @CreatedDate, @LastModifiedDate 등의 어노테이션과 함께 사용
 */