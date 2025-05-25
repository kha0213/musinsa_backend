package com.yl.musinsa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing 설정
 * - @CreatedDate, @LastModifiedDate 등 JPA Auditing 기능 활성화
 * - 테스트에서 선택적으로 제외 가능하도록 별도 Configuration으로 분리
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
