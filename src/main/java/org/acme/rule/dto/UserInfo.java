package org.acme.rule.dto;

import java.io.Serializable;

/**
 * 사용자/조직 정보 (ZORDMS0350010 의 사용자 조직 정보 영역).
 */
public record UserInfo(
        String ngmsUserId,
        String ngmsLoginId,
        String ngmsBrTypCd,
        String ngmsAuthId,
        String ngmsConnSaleOrgId,
        String ngmsPostSaleOrgId,
        String opDtm
) implements Serializable {}
