package org.acme.rule.dto;

import org.acme.runtime.ActionPlan;
import org.acme.runtime.ValidationError;

import java.io.Serializable;
import java.util.List;

/**
 * 룰 평가 결과 응답.
 * processable: "Y" (모든 단계 통과) / "N" (하나 이상 STOP)
 * message: 첫 STOP 메시지 또는 "OK"
 */
public record ProcessingResult(
        String processable,
        String message,
        List<ValidationError> errors,
        ActionPlan actionPlan
) implements Serializable {}
