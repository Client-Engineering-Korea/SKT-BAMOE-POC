package com.telco.model.context;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 트랜잭션 컨텍스트 (CSV 외부 데이터)
 * API 요청 및 시스템 컨텍스트에서 획득하는 트랜잭션 관련 정보
 */
public class TransactionContext implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 비즈니스 프로세스 서비스 트랜잭션 ID
     */
    private String businessProcessServiceTransactionId;


    /**
     * 채널코드
     * Rule 2, 3에서 사용 (예: POS, OMD)
     */
    @NotNull(message = "채널코드는 필수입니다")
    private String channelCode;
    
    /**
     * 변경유형
     * Rule 6에서 사용 (예: NEW, CHANGE, TERMINATE)
     */
    @NotNull(message = "변경유형은 필수입니다")
    private String changeType;
    
    /**
     * 대상요금제
     * 변경 대상 요금제 (변경 시 필수)
     */
    private String targetProductId;
    
    /**
     * 트랜잭션ID
     * 트랜잭션 식별자 (시스템 생성)
     */
    private String transactionId;
    
    /**
     * 요청시각
     * 요청 시각 (시스템 시간)
     */
    private LocalDateTime requestTimestamp;
    
    /**
     * 사용자권한
     * 사용자 권한 정보
     */
    private String userRole;
    
    /**
     * 온라인배치구분
     * Rule 2에서 필수 (O=온라인, B=배치)
     */
    @NotNull(message = "온라인배치구분은 필수입니다")
    private String onlineBatchCode;
    
    /**
     * 서비스변경코드
     * Rule 1에서 사용 - 서비스 변경 유형을 나타내는 코드
     * 예: G1(일반변경), D4(해지), D5(해지), I1(신규), I2(신규)
     */
    private String serviceChangeCode;
    
    /**
     * 권한코드 집합
     * Rule 1에서 사용 - 사용자가 보유한 권한 코드들
     * 예: ORDAU5898 (특정 처리 권한)
     */
    private List<String> authorities;
    
    /**
     * POS 미처리 대상 여부 (Rule 2용)
     * 기본값: true (처리 대상)
     */
    private Boolean isPOSProcessingTarget_EXT;
    
    /**
     * WiBro 통합조절요금 적용 여부 (Rule 5용)
     * 기본값: true (적용 대상)
     */
    private Boolean isWiBroRateApplicable_EXT;
    
    /**
     * 요금제 변경 제한 여부 (Rule 6용)
     * 기본값: true (제한 대상)
     */
    private Boolean isRateChangeRestricted_EXT;

    /**
     * 변경업무처리 불가대상 요금제 여부 (Rule 6용)
     * 기본값: false (처리 가능)
     */
    private Boolean isChangeBusinessProcessingImpossibleTargetPlan_EXT;

    /**
     * OMD 여부 단말기능 존재여부 (Rule 3용)
     * 기본값: "Y"
     */
    private String hasTerminalFunctionYn_EXT;

    /** 
     * OMD단말 가입 불가 요금제 상품그룹 존재여부 (Rule 3용) 
     * 기본값: "Y"
    */
    private String hasProductGroupExistYn_EXT;

    /**
     * ORDAU5379 권한자여부 (Rule 3용)
     * 기본값: "N"
     */
    private String hasOmdChannelPlanJoinAuthorityYn_EXT;

    /**
     * TMS요금제(특수단말)여부 단말기능 존재여부 (Rule 4용)
     * 기본값: "N"
     */
    private String hasTmsTerminalFunctionYn_EXT;

    /**
     * 전용요금제대상 단말기(EXCLSV_PRC_MDL) 추가단말정의 존재여부 (Rule 4용)
     * 기본값: "N"
     */
    private String hasExclusivePriceModelDefinitionYn_EXT;

    /**
     * M2M전용요금제사용대상단말(M2M_EXCLSV_MDL) 추가단말상품그룹 존재여부 (Rule 4용)
     * 기본값: "N"
     */
    private String hasM2mExclusiveProductGroupYn_EXT;

    /**
     * M2M특정요금제외 타요금제로의 변경가능권한(ORDAU5797) 권한자여부 (Rule 4용)
     * 기본값: "N"
     */
    private String hasM2mPlanChangeAuthorityYn_EXT;

    /**
     * 애플워치 자회선 개통 진행중 여부 (Rule 7용)
     * ITM0000976 | zord_rsp_svc_dtl.rsp_svc_op_st_cd
     * 기본값: "N"
     */
    private String isAppleWatchActivationInProgress_EXT;

    // Constructors
    
    /**
     * Default constructor
     */
    public TransactionContext() {
        this.requestTimestamp = LocalDateTime.now();
        this.authorities = new ArrayList<>();
        this.businessProcessServiceTransactionId = null;
        this.isPOSProcessingTarget_EXT = true;
        this.isWiBroRateApplicable_EXT = true;
        this.isRateChangeRestricted_EXT = true;
        this.isChangeBusinessProcessingImpossibleTargetPlan_EXT = false;
        this.hasTerminalFunctionYn_EXT = "Y";
        this.hasProductGroupExistYn_EXT = "Y";
        this.hasOmdChannelPlanJoinAuthorityYn_EXT = "N";
        this.hasTmsTerminalFunctionYn_EXT = "N";
        this.hasExclusivePriceModelDefinitionYn_EXT = "N";
        this.hasM2mExclusiveProductGroupYn_EXT = "N";
        this.hasM2mPlanChangeAuthorityYn_EXT = "N";
        this.isAppleWatchActivationInProgress_EXT = "N";
    }
    
    /**
     * All-args constructor
     */
    public TransactionContext(String exceptionTransactionId,
                             String channelCode, String changeType, String targetProductId,
                             String transactionId, LocalDateTime requestTimestamp, String userRole,
                             String onlineBatchCode, String serviceChangeCode, List<String> authorities,
                             Boolean isPOSProcessingTarget, Boolean isWiBroRateApplicable,
                             Boolean isRateChangeRestricted, Boolean isChangeBusinessProcessingImpossibleTargetPlan,
                             String hasTerminalFunctionYn, String hasProductGroupExistYn,
                             String hasOmdChannelPlanJoinAuthorityYn,
                             String hasTmsTerminalFunctionYn, String hasExclusivePriceModelDefinitionYn,
                             String hasM2mExclusiveProductGroupYn, String hasM2mPlanChangeAuthorityYn,
                             String isAppleWatchActivationInProgress) {
        this.businessProcessServiceTransactionId = exceptionTransactionId;
        this.channelCode = channelCode;
        this.changeType = changeType;
        this.targetProductId = targetProductId;
        this.transactionId = transactionId;
        this.requestTimestamp = requestTimestamp;
        this.userRole = userRole;
        this.onlineBatchCode = onlineBatchCode;
        this.serviceChangeCode = serviceChangeCode;
        this.authorities = authorities;
        this.isPOSProcessingTarget_EXT = isPOSProcessingTarget;
        this.isWiBroRateApplicable_EXT = isWiBroRateApplicable;
        this.isRateChangeRestricted_EXT = isRateChangeRestricted;
        this.isChangeBusinessProcessingImpossibleTargetPlan_EXT = isChangeBusinessProcessingImpossibleTargetPlan;
        this.hasTerminalFunctionYn_EXT = hasTerminalFunctionYn;
        this.hasProductGroupExistYn_EXT = hasProductGroupExistYn;
        this.hasOmdChannelPlanJoinAuthorityYn_EXT = hasOmdChannelPlanJoinAuthorityYn;
        this.hasTmsTerminalFunctionYn_EXT = hasTmsTerminalFunctionYn;
        this.hasExclusivePriceModelDefinitionYn_EXT = hasExclusivePriceModelDefinitionYn;
        this.hasM2mExclusiveProductGroupYn_EXT = hasM2mExclusiveProductGroupYn;
        this.hasM2mPlanChangeAuthorityYn_EXT = hasM2mPlanChangeAuthorityYn;
        this.isAppleWatchActivationInProgress_EXT = isAppleWatchActivationInProgress;
    }
    
    // Getters and Setters
    
    public String getBusinessProcessServiceTransactionId() {
        return businessProcessServiceTransactionId;
    }

    public void setBusinessProcessServiceTransactionId(String exceptionTransactionId) {
        this.businessProcessServiceTransactionId = exceptionTransactionId;
    }

    public String getChannelCode() {
        return channelCode;
    }
    
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    
    public String getChangeType() {
        return changeType;
    }
    
    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }
    
    public String getTargetProductId() {
        return targetProductId;
    }
    
    public void setTargetProductId(String targetProductId) {
        this.targetProductId = targetProductId;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public LocalDateTime getRequestTimestamp() {
        return requestTimestamp;
    }
    
    public void setRequestTimestamp(LocalDateTime requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }
    
    public String getUserRole() {
        return userRole;
    }
    
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    public String getOnlineBatchCode() {
        return onlineBatchCode;
    }
    
    public void setOnlineBatchCode(String onlineBatchCode) {
        this.onlineBatchCode = onlineBatchCode;
    }
    
    public String getServiceChangeCode() {
        return serviceChangeCode;
    }
    
    public void setServiceChangeCode(String serviceChangeCode) {
        this.serviceChangeCode = serviceChangeCode;
    }
    
    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
    
    public Boolean getIsPOSProcessingTarget_EXT() {
        return isPOSProcessingTarget_EXT;
    }

    public Boolean isIsPOSProcessingTarget_EXT() {
        return isPOSProcessingTarget_EXT;
    }

    public void setIsPOSProcessingTarget_EXT(Boolean isPOSProcessingTarget) {
        this.isPOSProcessingTarget_EXT = isPOSProcessingTarget;
    }

    public Boolean getIsWiBroRateApplicable_EXT() {
        return isWiBroRateApplicable_EXT;
    }

    public Boolean isIsWiBroRateApplicable_EXT() {
        return isWiBroRateApplicable_EXT;
    }

    public void setIsWiBroRateApplicable_EXT(Boolean isWiBroRateApplicable) {
        this.isWiBroRateApplicable_EXT = isWiBroRateApplicable;
    }

    public Boolean getIsRateChangeRestricted_EXT() {
        return isRateChangeRestricted_EXT;
    }

    public Boolean isIsRateChangeRestricted_EXT() {
        return isRateChangeRestricted_EXT;
    }

    public void setIsRateChangeRestricted_EXT(Boolean isRateChangeRestricted) {
        this.isRateChangeRestricted_EXT = isRateChangeRestricted;
    }

    public Boolean getIsChangeBusinessProcessingImpossibleTargetPlan_EXT() {
        return isChangeBusinessProcessingImpossibleTargetPlan_EXT;
    }

    public Boolean isIsChangeBusinessProcessingImpossibleTargetPlan_EXT() {
        return isChangeBusinessProcessingImpossibleTargetPlan_EXT;
    }

    public void setIsChangeBusinessProcessingImpossibleTargetPlan_EXT(Boolean isChangeBusinessProcessingImpossibleTargetPlan) {
        this.isChangeBusinessProcessingImpossibleTargetPlan_EXT = isChangeBusinessProcessingImpossibleTargetPlan;
    }

    public String getHasTerminalFunctionYn_EXT() {
        return hasTerminalFunctionYn_EXT;
    }

    public void setHasTerminalFunctionYn_EXT(String hasTerminalFunctionYn) {
        this.hasTerminalFunctionYn_EXT = hasTerminalFunctionYn;
    }

    public String getHasProductGroupExistYn_EXT() {
        return hasProductGroupExistYn_EXT;
    }

    public void setHasProductGroupExistYn_EXT(String hasProductGroupExistYn) {
        this.hasProductGroupExistYn_EXT = hasProductGroupExistYn;
    }

    public String getHasOmdChannelPlanJoinAuthorityYn_EXT() {
        return hasOmdChannelPlanJoinAuthorityYn_EXT;
    }
public void setHasOmdChannelPlanJoinAuthorityYn_EXT(String hasOmdChannelPlanJoinAuthorityYn) {
    this.hasOmdChannelPlanJoinAuthorityYn_EXT = hasOmdChannelPlanJoinAuthorityYn;
}

public String getHasTmsTerminalFunctionYn_EXT() {
    return hasTmsTerminalFunctionYn_EXT;
}

public void setHasTmsTerminalFunctionYn_EXT(String hasTmsTerminalFunctionYn) {
    this.hasTmsTerminalFunctionYn_EXT = hasTmsTerminalFunctionYn;
}

public String getHasExclusivePriceModelDefinitionYn_EXT() {
    return hasExclusivePriceModelDefinitionYn_EXT;
}

public void setHasExclusivePriceModelDefinitionYn_EXT(String hasExclusivePriceModelDefinitionYn) {
    this.hasExclusivePriceModelDefinitionYn_EXT = hasExclusivePriceModelDefinitionYn;
}

public String getHasM2mExclusiveProductGroupYn_EXT() {
    return hasM2mExclusiveProductGroupYn_EXT;
}

public void setHasM2mExclusiveProductGroupYn_EXT(String hasM2mExclusiveProductGroupYn) {
    this.hasM2mExclusiveProductGroupYn_EXT = hasM2mExclusiveProductGroupYn;
}

public String getHasM2mPlanChangeAuthorityYn_EXT() {
    return hasM2mPlanChangeAuthorityYn_EXT;
}

public void setHasM2mPlanChangeAuthorityYn_EXT(String hasM2mPlanChangeAuthorityYn) {
    this.hasM2mPlanChangeAuthorityYn_EXT = hasM2mPlanChangeAuthorityYn;
}

public String getIsAppleWatchActivationInProgress_EXT() {
    return isAppleWatchActivationInProgress_EXT;
}

public void setIsAppleWatchActivationInProgress_EXT(String isAppleWatchActivationInProgress) {
    this.isAppleWatchActivationInProgress_EXT = isAppleWatchActivationInProgress;
}


    /**
     * 특정 권한 코드 보유 여부 확인
     * Rule 1 DMN에서 사용: context.transaction.hasAuthority("ORDAU5898")
     *
     * @param authorityCode 확인할 권한 코드
     * @return 권한 보유 여부
     */
    public boolean hasAuthority(String authorityCode) {
        return authorities != null && authorities.contains(authorityCode);
    }
    
    // Builder
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String exceptionTransactionId;
        private String channelCode;
        private String changeType;
        private String targetProductId;
        private String transactionId;
        private LocalDateTime requestTimestamp = LocalDateTime.now();
        private String userRole;
        private String onlineBatchCode;
        private String serviceChangeCode;
        private List<String> authorities = new ArrayList<>();
        private Boolean isPOSProcessingTarget = true;
        private Boolean isWiBroRateApplicable = true;
        private Boolean isRateChangeRestricted = true;
        private Boolean isChangeBusinessProcessingImpossibleTargetPlan = false;
        private String hasTerminalFunctionYn = "Y";
        private String hasProductGroupExistYn = "Y";
        private String hasOmdChannelPlanJoinAuthorityYn = "N";
        private String hasTmsTerminalFunctionYn = "N";
        private String hasExclusivePriceModelDefinitionYn = "N";
        private String hasM2mExclusiveProductGroupYn = "N";
        private String hasM2mPlanChangeAuthorityYn = "N";
        private String isAppleWatchActivationInProgress = "N";

        private Builder() {
        }
        
        public Builder exceptionTransactionId(String exceptionTransactionId) {
            this.exceptionTransactionId = exceptionTransactionId;
            return this;
        }

        public Builder channelCode(String channelCode) {
            this.channelCode = channelCode;
            return this;
        }
        
        public Builder changeType(String changeType) {
            this.changeType = changeType;
            return this;
        }
        
        public Builder targetProductId(String targetProductId) {
            this.targetProductId = targetProductId;
            return this;
        }
        
        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }
        
        public Builder requestTimestamp(LocalDateTime requestTimestamp) {
            this.requestTimestamp = requestTimestamp;
            return this;
        }
        
        public Builder userRole(String userRole) {
            this.userRole = userRole;
            return this;
        }
        
        public Builder onlineBatchCode(String onlineBatchCode) {
            this.onlineBatchCode = onlineBatchCode;
            return this;
        }
        
        public Builder serviceChangeCode(String serviceChangeCode) {
            this.serviceChangeCode = serviceChangeCode;
            return this;
        }
        
        public Builder authorities(List<String> authorities) {
            this.authorities = authorities;
            return this;
        }
        
        public Builder isPOSProcessingTarget(Boolean isPOSProcessingTarget) {
            this.isPOSProcessingTarget = isPOSProcessingTarget;
            return this;
        }
        
        public Builder isWiBroRateApplicable(Boolean isWiBroRateApplicable) {
            this.isWiBroRateApplicable = isWiBroRateApplicable;
            return this;
        }
        
        public Builder isRateChangeRestricted(Boolean isRateChangeRestricted) {
            this.isRateChangeRestricted = isRateChangeRestricted;
            return this;
        }

        public Builder isChangeBusinessProcessingImpossibleTargetPlan(Boolean isChangeBusinessProcessingImpossibleTargetPlan) {
            this.isChangeBusinessProcessingImpossibleTargetPlan = isChangeBusinessProcessingImpossibleTargetPlan;
            return this;
        }

        public Builder hasTerminalFunctionYn(String hasTerminalFunctionYn) {
            this.hasTerminalFunctionYn = hasTerminalFunctionYn;
            return this;
        }

        public Builder hasProductGroupExistYn(String hasProductGroupExistYn) {
            this.hasProductGroupExistYn = hasProductGroupExistYn;
            return this;
        }

        public Builder hasOmdChannelPlanJoinAuthorityYn(String hasOmdChannelPlanJoinAuthorityYn) {
            this.hasOmdChannelPlanJoinAuthorityYn = hasOmdChannelPlanJoinAuthorityYn;
            return this;
        }

        public Builder hasTmsTerminalFunctionYn(String hasTmsTerminalFunctionYn) {
            this.hasTmsTerminalFunctionYn = hasTmsTerminalFunctionYn;
            return this;
        }

        public Builder hasExclusivePriceModelDefinitionYn(String hasExclusivePriceModelDefinitionYn) {
            this.hasExclusivePriceModelDefinitionYn = hasExclusivePriceModelDefinitionYn;
            return this;
        }

        public Builder hasM2mExclusiveProductGroupYn(String hasM2mExclusiveProductGroupYn) {
            this.hasM2mExclusiveProductGroupYn = hasM2mExclusiveProductGroupYn;
            return this;
        }

        public Builder hasM2mPlanChangeAuthorityYn(String hasM2mPlanChangeAuthorityYn) {
            this.hasM2mPlanChangeAuthorityYn = hasM2mPlanChangeAuthorityYn;
            return this;
        }

        public Builder isAppleWatchActivationInProgress(String isAppleWatchActivationInProgress) {
            this.isAppleWatchActivationInProgress = isAppleWatchActivationInProgress;
            return this;
        }

        public TransactionContext build() {
            return new TransactionContext(exceptionTransactionId,
                    channelCode, changeType, targetProductId,
                    transactionId, requestTimestamp, userRole, onlineBatchCode,
                    serviceChangeCode, authorities, isPOSProcessingTarget,
                    isWiBroRateApplicable, isRateChangeRestricted,
                    isChangeBusinessProcessingImpossibleTargetPlan,
                    hasTerminalFunctionYn, hasProductGroupExistYn,
                    hasOmdChannelPlanJoinAuthorityYn,
                    hasTmsTerminalFunctionYn, hasExclusivePriceModelDefinitionYn,
                    hasM2mExclusiveProductGroupYn, hasM2mPlanChangeAuthorityYn,
                    isAppleWatchActivationInProgress);
        }
    }
    
    // equals, hashCode, toString
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionContext that = (TransactionContext) o;
        return Objects.equals(businessProcessServiceTransactionId, that.businessProcessServiceTransactionId) &&
                Objects.equals(channelCode, that.channelCode) &&
                Objects.equals(changeType, that.changeType) &&
                Objects.equals(targetProductId, that.targetProductId) &&
                Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(requestTimestamp, that.requestTimestamp) &&
                Objects.equals(userRole, that.userRole) &&
                Objects.equals(onlineBatchCode, that.onlineBatchCode) &&
                Objects.equals(serviceChangeCode, that.serviceChangeCode) &&
                Objects.equals(authorities, that.authorities) &&
                Objects.equals(isPOSProcessingTarget_EXT, that.isPOSProcessingTarget_EXT) &&
                Objects.equals(isWiBroRateApplicable_EXT, that.isWiBroRateApplicable_EXT) &&
                Objects.equals(isRateChangeRestricted_EXT, that.isRateChangeRestricted_EXT) &&
                Objects.equals(isChangeBusinessProcessingImpossibleTargetPlan_EXT, that.isChangeBusinessProcessingImpossibleTargetPlan_EXT) &&
                Objects.equals(hasTerminalFunctionYn_EXT, that.hasTerminalFunctionYn_EXT) &&
                Objects.equals(hasProductGroupExistYn_EXT, that.hasProductGroupExistYn_EXT) &&
                Objects.equals(hasOmdChannelPlanJoinAuthorityYn_EXT, that.hasOmdChannelPlanJoinAuthorityYn_EXT);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(businessProcessServiceTransactionId, channelCode, changeType, targetProductId, transactionId,
                requestTimestamp, userRole, onlineBatchCode, serviceChangeCode,
                authorities, isPOSProcessingTarget_EXT, isWiBroRateApplicable_EXT,
                isRateChangeRestricted_EXT, isChangeBusinessProcessingImpossibleTargetPlan_EXT,
                hasTerminalFunctionYn_EXT, hasProductGroupExistYn_EXT, hasOmdChannelPlanJoinAuthorityYn_EXT,
                hasTmsTerminalFunctionYn_EXT, hasExclusivePriceModelDefinitionYn_EXT,
                hasM2mExclusiveProductGroupYn_EXT, hasM2mPlanChangeAuthorityYn_EXT);
    }
    
    @Override
    public String toString() {
        return "TransactionContext{" +
                "exceptionTransactionId='" + businessProcessServiceTransactionId + '\'' +
                ", channelCode='" + channelCode + '\'' +
                ", changeType='" + changeType + '\'' +
                ", targetProductId='" + targetProductId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", requestTimestamp=" + requestTimestamp +
                ", userRole='" + userRole + '\'' +
                ", onlineBatchCode='" + onlineBatchCode + '\'' +
                ", serviceChangeCode='" + serviceChangeCode + '\'' +
                ", authorities=" + authorities +
                ", isPOSProcessingTarget=" + isPOSProcessingTarget_EXT +
                ", isWiBroRateApplicable=" + isWiBroRateApplicable_EXT +
                ", isRateChangeRestricted=" + isRateChangeRestricted_EXT +
                ", isChangeBusinessProcessingImpossibleTargetPlan=" + isChangeBusinessProcessingImpossibleTargetPlan_EXT +
                ", hasTerminalFunctionYn=" + hasTerminalFunctionYn_EXT +
                ", hasProductGroupExistYn=" + hasProductGroupExistYn_EXT +
                ", hasOmdChannelPlanJoinAuthorityYn=" + hasOmdChannelPlanJoinAuthorityYn_EXT +
                ", hasTmsTerminalFunctionYn=" + hasTmsTerminalFunctionYn_EXT +
                ", hasExclusivePriceModelDefinitionYn=" + hasExclusivePriceModelDefinitionYn_EXT +
                ", hasM2mExclusiveProductGroupYn=" + hasM2mExclusiveProductGroupYn_EXT +
                ", hasM2mPlanChangeAuthorityYn=" + hasM2mPlanChangeAuthorityYn_EXT +
                '}';
    }
}

// Made with Bob
