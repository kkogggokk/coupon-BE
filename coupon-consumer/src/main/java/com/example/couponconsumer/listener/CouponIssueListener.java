package com.example.couponconsumer.listener;

import com.example.couponcore.repository.redis.RedisRepository;
import com.example.couponcore.repository.redis.dto.CouponIssueRequest;
import com.example.couponcore.service.CouponIssueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.example.couponcore.util.CouponRedisUtils.getIssueRequestQueueKey;

@RequiredArgsConstructor
@EnableScheduling
@Component
public class CouponIssueListener {

    private final CouponIssueService couponIssueService;
    private final RedisRepository redisRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String issueRequestQueueKey = getIssueRequestQueueKey();
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Scheduled(fixedDelay = 1000)
    public void issue() throws JsonProcessingException {// 쿠폰 발급 대기열 큐를 읽어서 쿠폰 발급 처리 기능
        log.info("listen...");
        while (existCouponIssueTarget()) {
            CouponIssueRequest target = getIssueTarget();
            log.info("발급 시작 target: " + target);
            couponIssueService.issue(target.couponId(), target.userId()); // DB로 트랜잭션
            log.info("발급 완료 target: " + target);
            removeIssuedTarget();
        }
    }

    private boolean existCouponIssueTarget() {  // 레디스 레포지토리의 큐의 사이즈를 확인하여 대상이 있는지를 확인한다. 레디스 레포지토리에서 lSize메소드를 생성
        return redisRepository.lSize(issueRequestQueueKey) > 0;
    }

    private CouponIssueRequest getIssueTarget() throws JsonProcessingException {     // 레디스에서 값 읽어오기 , 레디스 레포지토리 lIndex 메소드 생성. 인덱스를 통해서 값을 읽어오기. // 역직렬화 하기 위해서 오브젝터 매퍼를 사용
        return objectMapper.readValue(redisRepository.lIndex(issueRequestQueueKey, 0), CouponIssueRequest.class);
    }

    private void removeIssuedTarget() { // 발급완료 되면 pop해서 제거하기
        redisRepository.lPop(issueRequestQueueKey);
    }
}
