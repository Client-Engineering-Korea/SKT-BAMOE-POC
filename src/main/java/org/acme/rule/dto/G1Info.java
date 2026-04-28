package org.acme.rule.dto;

import java.io.Serializable;

/**
 * G1(요금제변경) 전용 입력.
 * befFeeProdId: 변경 전 요금제 ID
 * aftFeeProdId: 변경 후 요금제 ID
 */
public record G1Info(
        String befFeeProdId,
        String aftFeeProdId
) implements Serializable {}
