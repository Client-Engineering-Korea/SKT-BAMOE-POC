package org.acme.rule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 빈 설정 (외부 fact API HTTP 호출용).
 *
 * <p>모든 profile 에서 활성화. fact-server profile 에서도 빈으로 등록되지만
 * RuleCheckController/Service 가 비활성이라 실제 호출 경로는 차단됨.
 *
 * <p>이유: Kogito 가 BPMN ServiceTask 의 자동 생성 핸들러
 * ({@code FactPrefetchService_populate_..._Handler}) 를 항상 빈으로 등록하는데,
 * 이 핸들러가 FactPrefetchService → RestTemplate 의존성을 주입받으므로
 * RestTemplate 빈도 항상 등록되어야 함.
 */
@Configuration
public class RuleClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
