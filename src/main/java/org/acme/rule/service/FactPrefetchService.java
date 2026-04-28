package org.acme.rule.service;

import org.acme.fact.domain.CustService;
import org.acme.fact.domain.EquipmentModel;
import org.acme.rule.dto.RatePlanChangeRequest;
import org.acme.runtime.ProcessingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * BPMN ServiceTask 에서 호출되는 fact prefetch 빈.
 *
 * <p>외부 fact API 를 HTTP 로 호출하여 CustService + EquipmentModel 을 조회.
 * fact API 는 별도 Spring Boot 인스턴스 (profile=fact-server, port 8081 기본) 로 떠있어야 함.
 *
 * <p><b>dual constructor 이유</b> (test_2 함정 #7):
 * Kogito 가 ServiceTask 의 {@code drools:serviceinterface} 로 지정된 클래스를
 * {@code new FactPrefetchService()} (no-arg) 로 인스턴스화하므로 default 생성자가 필요하다.
 * 동시에 Spring DI 로 RestTemplate + base URL 이 주입되도록 {@code @Autowired} 생성자를
 * 둔다. static 필드 로 bean 레퍼런스를 보관해 두 인스턴스 모두 동일 빈을 사용.
 *
 * <p><b>Profile</b>: fact-server profile 에서는 비활성 (fact API 만 떠있는 인스턴스).
 */
@Component
public class FactPrefetchService {

    private static RestTemplate restTemplateRef;
    private static String factApiBaseUrlRef;

    /** Kogito 가 호출하는 no-arg constructor. static bean 레퍼런스 사용. */
    public FactPrefetchService() {}

    /** Spring 이 호출하는 DI constructor. static field 로 bean 레퍼런스 보관. */
    @Autowired
    public FactPrefetchService(RestTemplate restTemplate,
                                @Value("${fact.api.base-url}") String factApiBaseUrl) {
        restTemplateRef = restTemplate;
        factApiBaseUrlRef = factApiBaseUrl;
    }

    /**
     * ProcessingContext 의 svcMgmtNum 으로 외부 fact API 를 HTTP 호출하여 ctx 에 fact 채움.
     *
     * @param ctx 빈 custService/eqpModel 슬롯이 있는 ProcessingContext
     * @return 동일 ctx (fact 채워진 상태) — BPMN dataOutput 매핑용
     */
    public ProcessingContext populate(ProcessingContext ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("ProcessingContext is null");
        }
        RatePlanChangeRequest req = ctx.getRequest();
        if (req == null || req.svcMgmtNum() == null || req.svcMgmtNum().isBlank()) {
            throw new IllegalArgumentException("svcMgmtNum is required");
        }

        String custUrl = factApiBaseUrlRef + "/api/fact/cust/getCustService/" + req.svcMgmtNum();
        CustService custService = fetchOrThrow(custUrl, CustService.class,
                "Customer not found: " + req.svcMgmtNum());
        ctx.setCustService(custService);

        if (custService.eqpMdlCd() != null && !custService.eqpMdlCd().isBlank()) {
            String eqpUrl = factApiBaseUrlRef + "/api/fact/eqp/getEquipmentModel/"
                    + custService.eqpMdlCd();
            EquipmentModel eqpModel = fetchOrNull(eqpUrl, EquipmentModel.class);
            ctx.setEqpModel(eqpModel);
        }
        return ctx;
    }

    private <T> T fetchOrThrow(String url, Class<T> type, String notFoundMsg) {
        try {
            T result = restTemplateRef.getForObject(url, type);
            if (result == null) {
                throw new IllegalArgumentException(notFoundMsg);
            }
            return result;
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException(notFoundMsg, e);
        }
    }

    private <T> T fetchOrNull(String url, Class<T> type) {
        try {
            return restTemplateRef.getForObject(url, type);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
