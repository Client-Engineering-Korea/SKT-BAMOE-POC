package org.acme.fact.domain;

import java.util.Map;

// 단말모델 정보 Record
public record EquipmentModel(
        String eqpMdlCd,    // 단말모델코드
        String eqpMdlNm,    // 단말모델명
        String verNum,      // 버전번호
        Map<String, EquipmentModelLineup> eqpMdlLnupMap  // 단말모델라인업
) {
    // 단말모델 라인업 정보 Record
    public record EquipmentModelLineup(
            String lnupItmCd,       // 라인업아이템코드
            String lnupItmNm,       // 라인업아이템명
            String lnupDtlItmCd,    // 라인업상세아이템코드
            String lnupDtlItmNm     // 라인업상세아이템명
    ) {}
}
