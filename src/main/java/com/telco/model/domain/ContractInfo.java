package com.telco.model.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 계약 정보
 * 고객의 이용 계약 관련 정보를 담는 도메인 모델
 */
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ContractInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 계약관리번호
     * 계약을 식별하는 고유 번호
     */
    @NotNull(message = "계약관리번호는 필수입니다")
    @Size(min = 10, max = 10)
    private String contractManagementNumber;
    
    /**
     * 이용계약구분코드
     * 계약의 유형을 나타내는 코드
     */
    @NotNull(message = "이용계약구분코드는 필수입니다")
    @Pattern(regexp = "[0-9]")
    private String contractClassCode;
    
    /**
     * 이용계약상태코드
     * 계약의 현재 상태 (예: AC=Active)
     */
    @NotNull(message = "이용계약상태코드는 필수입니다")
    @Size(min = 2, max = 2)
    private String contractStatusCode;
    
    /**
     * 계약일자
     * 계약이 체결된 날짜
     */
    @NotNull(message = "계약일자는 필수입니다")
    @Past
    private LocalDate contractDate;
    
    /**
     * 회사구분코드
     * 통신사 구분 (예: T=SKT)
     */
    @NotNull(message = "회사구분코드는 필수입니다")
    @Size(min = 1, max = 1)
    private String companyClassCode;
    
    /**
     * 세금계산서발행여부
     * 세금계산서 발행 여부 (Y/N → Boolean)
     */
    @NotNull(message = "세금계산서발행여부는 필수입니다")
    private Boolean taxBillIssueYn;
    
    /**
     * 가입해지코드
     * Rule 1에서 사용 - 가입 해지 사유를 나타내는 코드
     * 예: 01 (특정 해지 사유)
     */
    @Size(min = 2, max = 2)
    @Pattern(regexp = "[0-9]{2}", message = "가입해지코드는 2자리 숫자여야 합니다")
    private String subscriptionCancelCode;
    
    // Constructors
    
    /**
     * Default constructor
     */
    public ContractInfo() {
    }
    
    /**
     * All-args constructor
     */
    public ContractInfo(String contractManagementNumber, String contractClassCode,
                       String contractStatusCode, LocalDate contractDate,
                       String companyClassCode, Boolean taxBillIssueYn,
                       String subscriptionCancelCode) {
        this.contractManagementNumber = contractManagementNumber;
        this.contractClassCode = contractClassCode;
        this.contractStatusCode = contractStatusCode;
        this.contractDate = contractDate;
        this.companyClassCode = companyClassCode;
        this.taxBillIssueYn = taxBillIssueYn;
        this.subscriptionCancelCode = subscriptionCancelCode;
    }
    
    // Getters and Setters
    
    public String getContractManagementNumber() {
        return contractManagementNumber;
    }
    
    public void setContractManagementNumber(String contractManagementNumber) {
        this.contractManagementNumber = contractManagementNumber;
    }
    
    public String getContractClassCode() {
        return contractClassCode;
    }
    
    public void setContractClassCode(String contractClassCode) {
        this.contractClassCode = contractClassCode;
    }
    
    public String getContractStatusCode() {
        return contractStatusCode;
    }
    
    public void setContractStatusCode(String contractStatusCode) {
        this.contractStatusCode = contractStatusCode;
    }
    
    public LocalDate getContractDate() {
        return contractDate;
    }
    
    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }
    
    public String getCompanyClassCode() {
        return companyClassCode;
    }
    
    public void setCompanyClassCode(String companyClassCode) {
        this.companyClassCode = companyClassCode;
    }
    
    public Boolean getTaxBillIssueYn() {
        return taxBillIssueYn;
    }

    public Boolean isTaxBillIssueYn() {
        return taxBillIssueYn;
    }
    
    public void setTaxBillIssueYn(Boolean taxBillIssueYn) {
        this.taxBillIssueYn = taxBillIssueYn;
    }
    
    public String getSubscriptionCancelCode() {
        return subscriptionCancelCode;
    }
    
    public void setSubscriptionCancelCode(String subscriptionCancelCode) {
        this.subscriptionCancelCode = subscriptionCancelCode;
    }
    
    // Builder
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String contractManagementNumber;
        private String contractClassCode;
        private String contractStatusCode;
        private LocalDate contractDate;
        private String companyClassCode;
        private Boolean taxBillIssueYn;
        private String subscriptionCancelCode;
        
        private Builder() {
        }
        
        public Builder contractManagementNumber(String contractManagementNumber) {
            this.contractManagementNumber = contractManagementNumber;
            return this;
        }
        
        public Builder contractClassCode(String contractClassCode) {
            this.contractClassCode = contractClassCode;
            return this;
        }
        
        public Builder contractStatusCode(String contractStatusCode) {
            this.contractStatusCode = contractStatusCode;
            return this;
        }
        
        public Builder contractDate(LocalDate contractDate) {
            this.contractDate = contractDate;
            return this;
        }
        
        public Builder companyClassCode(String companyClassCode) {
            this.companyClassCode = companyClassCode;
            return this;
        }
        
        public Builder taxBillIssueYn(Boolean taxBillIssueYn) {
            this.taxBillIssueYn = taxBillIssueYn;
            return this;
        }
        
        public Builder subscriptionCancelCode(String subscriptionCancelCode) {
            this.subscriptionCancelCode = subscriptionCancelCode;
            return this;
        }
        
        public ContractInfo build() {
            return new ContractInfo(contractManagementNumber, contractClassCode,
                    contractStatusCode, contractDate, companyClassCode,
                    taxBillIssueYn, subscriptionCancelCode);
        }
    }
    
    // equals, hashCode, toString
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractInfo that = (ContractInfo) o;
        return Objects.equals(contractManagementNumber, that.contractManagementNumber) &&
                Objects.equals(contractClassCode, that.contractClassCode) &&
                Objects.equals(contractStatusCode, that.contractStatusCode) &&
                Objects.equals(contractDate, that.contractDate) &&
                Objects.equals(companyClassCode, that.companyClassCode) &&
                Objects.equals(taxBillIssueYn, that.taxBillIssueYn) &&
                Objects.equals(subscriptionCancelCode, that.subscriptionCancelCode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(contractManagementNumber, contractClassCode, contractStatusCode,
                contractDate, companyClassCode, taxBillIssueYn, subscriptionCancelCode);
    }
    
    @Override
    public String toString() {
        return "ContractInfo{" +
                "contractManagementNumber='" + contractManagementNumber + '\'' +
                ", contractClassCode='" + contractClassCode + '\'' +
                ", contractStatusCode='" + contractStatusCode + '\'' +
                ", contractDate=" + contractDate +
                ", companyClassCode='" + companyClassCode + '\'' +
                ", taxBillIssueYn=" + taxBillIssueYn +
                ", subscriptionCancelCode='" + subscriptionCancelCode + '\'' +
                '}';
    }
}

// Made with Bob
