package com.example.couponconsumer;

import com.example.couponcore.CouponCoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(CouponCoreConfiguration.class)
@SpringBootApplication
public class CouponConsumerApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-core,application-consumer");
        SpringApplication.run(CouponConsumerApplication.class, args);
    }

}


/*
    coupon-core 모듈을 Import하기 위해서 @Import 어노테이션을 사용
    application-core.yml, application-consumer.yml 설정 값을 반영하기 위해서 System.setProperty 설정
*/