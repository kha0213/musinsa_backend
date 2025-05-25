package com.yl.musinsa.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDSL 설정
 * - JPAQueryFactory 빈 등록
 * - EntityManager가 존재할 때만 활성화 (테스트 호환성)
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(EntityManager.class)  // EntityManager가 있을 때만 활성화
public class QueryDslConfig {
    
    private final EntityManager entityManager;
    
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
