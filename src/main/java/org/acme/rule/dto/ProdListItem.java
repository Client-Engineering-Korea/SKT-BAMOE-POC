package org.acme.rule.dto;

import java.io.Serializable;

/**
 * 처리 대상 상품 항목.
 * scrbTermClCd: 가입해지구분코드 (01=가입, 03=해지 등)
 */
public record ProdListItem(
        String prodId,
        String scrbTermClCd
) implements Serializable {}
