package com.telco.model.result;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 룰 실행 결과로 생성되는 검증 오류
 * fail-fast 패턴: 룰이 위반을 감지하면 이 객체를 세션에 insert
 */
public class ValidationError implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 조건 상세 타입 ID (원본 명세의 COND_DTL_TYP_ID)
     * 예: D000000295, D000000296
     */
    @NotNull
    private String condDtlTypId;

    /**
     * 그룹 조건 ID (원본 명세의 ECOND_GRP_ID)
     * 예: LOGCP00001
     */
    private String econdGrpId;

    /**
     * 오류 코드 (시스템 정의)
     * 예: ERR_SVC_TYP_001
     */
    @NotNull
    private String errorCode;

    /**
     * 사용자 노출 메시지
     */
    @NotNull
    private String errorMessage;

    /**
     * 심각도
     */
    @NotNull
    private Severity severity;

    /**
     * 발생한 룰 이름 (디버깅용)
     */
    private String ruleName;

    /**
     * 발생 시각
     */
    private LocalDateTime occurredAt;

    // Constructors

    public ValidationError() {
        this.occurredAt = LocalDateTime.now();
    }

    public ValidationError(String condDtlTypId, String econdGrpId,
                           String errorCode, String errorMessage,
                           Severity severity, String ruleName) {
        this.condDtlTypId = condDtlTypId;
        this.econdGrpId = econdGrpId;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.severity = severity;
        this.ruleName = ruleName;
        this.occurredAt = LocalDateTime.now();
    }

    // Getters and Setters

    public String getCondDtlTypId() { return condDtlTypId; }
    public void setCondDtlTypId(String v) { this.condDtlTypId = v; }

    public String getEcondGrpId() { return econdGrpId; }
    public void setEcondGrpId(String v) { this.econdGrpId = v; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String v) { this.errorCode = v; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String v) { this.errorMessage = v; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity v) { this.severity = v; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String v) { this.ruleName = v; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime v) { this.occurredAt = v; }

    // Builder

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String condDtlTypId;
        private String econdGrpId;
        private String errorCode;
        private String errorMessage;
        private Severity severity;
        private String ruleName;

        private Builder() {}

        public Builder condDtlTypId(String v) { this.condDtlTypId = v; return this; }
        public Builder econdGrpId(String v) { this.econdGrpId = v; return this; }
        public Builder errorCode(String v) { this.errorCode = v; return this; }
        public Builder errorMessage(String v) { this.errorMessage = v; return this; }
        public Builder severity(Severity v) { this.severity = v; return this; }
        public Builder ruleName(String v) { this.ruleName = v; return this; }

        public ValidationError build() {
            return new ValidationError(condDtlTypId, econdGrpId,
                    errorCode, errorMessage, severity, ruleName);
        }
    }

    // equals, hashCode, toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError that = (ValidationError) o;
        return Objects.equals(condDtlTypId, that.condDtlTypId) &&
                Objects.equals(econdGrpId, that.econdGrpId) &&
                Objects.equals(errorCode, that.errorCode) &&
                Objects.equals(errorMessage, that.errorMessage) &&
                severity == that.severity &&
                Objects.equals(ruleName, that.ruleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condDtlTypId, econdGrpId, errorCode,
                errorMessage, severity, ruleName);
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "condDtlTypId='" + condDtlTypId + '\'' +
                ", econdGrpId='" + econdGrpId + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", severity=" + severity +
                ", ruleName='" + ruleName + '\'' +
                ", occurredAt=" + occurredAt +
                '}';
    }
}
