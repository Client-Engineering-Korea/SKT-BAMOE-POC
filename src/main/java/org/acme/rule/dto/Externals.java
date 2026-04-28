package org.acme.rule.dto;

import java.io.Serializable;

/**
 * 외부 시스템 호출 결과 (PoC 단계에서는 시나리오 JSON 으로 직접 주입).
 *
 * <p>운영 환경에서는 각 _EXT 필드가 별도 외부 시스템 조회 결과로 채워짐.
 * 모든 Y/N 값은 String 으로 통일 (엑셀 IF_TXT 형식).
 */
public record Externals(
        String isPOSProcessingTargetExt,
        String hasTerminalFunctionYnExt,
        String hasProductGroupExistYnExt,
        String hasOmdChannelPlanJoinAuthorityYnExt,
        String hasExclusivePriceModelDefinitionYnExt,
        String hasM2mExclusiveProductGroupYnExt,
        String hasM2mPlanChangeAuthorityYnExt,
        String isAppleWatchActivationInProgressExt,
        String hasRoamingForceProcessAuthorityYnExt,
        String ibRoamingProductUsedYnExt,
        String ibRentRoamingProductUsedYnExt,
        String businessProcessServiceTransactionId,
        String isWiBroRateApplicableYnExt,
        String isRateChangeRestrictedYnExt
) implements Serializable {}
