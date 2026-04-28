package org.acme.rule.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 요금제 변경 자격 체크 요청 (ZORDMS0350010 입력 구조체 기반).
 *
 * <p>svcMgmtNum 으로 서버가 fact API 에서 CustService 와 EquipmentModel 을 prefetch 한 뒤,
 * 본 요청 정보와 결합해 ProcessingContext 를 조립하여 BPMN 에 전달.
 */
public record RatePlanChangeRequest(
        String svcMgmtNum,
        UserInfo userInfo,
        CommInfo commInfo,
        G1Info g1Info,
        List<ProdListItem> prodList,
        Externals externals
) implements Serializable {}
