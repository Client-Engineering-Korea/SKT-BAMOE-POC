package org.acme.fact.domain;

// 구성항목값 정보 Record
public record ConsItemValue(
        String consItmId,    // 구성항목ID
        String consItmNm,    // 구성항목명
        String consItmVal    // 구성항목값
) {}
