package com.telco.model.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 고객 정보
 * 명의 고객의 기본 정보 및 계정 정보를 담는 도메인 모델
 */
public class CustomerInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 명의고객번호
     * 고객을 식별하는 핵심 식별자
     */
    @NotNull(message = "명의고객번호는 필수입니다")
    @Size(min = 10, max = 10)
    private String customerNumber;
    
    /**
     * 계정번호
     * 청구 계정 번호
     */
    @NotNull(message = "계정번호는 필수입니다")
    @Size(min = 10, max = 10)
    private String accountNumber;
    
    /**
     * 주민법인사업자등록일련번호
     * 고객 식별을 위한 일련번호
     */
    @Size(min = 9, max = 9)
    private String citizenCorpBizSerialNumber;
    
    /**
     * 주민법인사업자등록번호부분정보
     * 개인정보 보호를 위한 마스킹된 정보
     */
    @Size(min = 13, max = 13)
    private String citizenCorpBizNumberPartialInfo;
    
    /**
     * 나이
     * 고객의 나이 (숫자 변환)
     */
    @Min(0)
    @Max(150)
    private Integer age;
    
    /**
     * 고객유형코드
     * 고객 유형 구분 (01=개인, 02=법인)
     */
    @NotNull(message = "고객유형코드는 필수입니다")
    @Size(min = 2, max = 2)
    private String customerTypeCode;
    
    /**
     * 고객세부유형코드
     * 고객의 세부 분류 코드
     */
    @Size(min = 2, max = 2)
    private String customerDetailTypeCode;
    
    /**
     * 계정유형코드
     * 계정 분류 코드
     */
    @NotNull(message = "계정유형코드는 필수입니다")
    @Size(min = 2, max = 2)
    private String accountTypeCode;
    
    // Constructors
    
    /**
     * Default constructor
     */
    public CustomerInfo() {
    }
    
    /**
     * All-args constructor
     */
    public CustomerInfo(String customerNumber, String accountNumber,
                       String citizenCorpBizSerialNumber, String citizenCorpBizNumberPartialInfo,
                       Integer age, String customerTypeCode, String customerDetailTypeCode,
                       String accountTypeCode) {
        this.customerNumber = customerNumber;
        this.accountNumber = accountNumber;
        this.citizenCorpBizSerialNumber = citizenCorpBizSerialNumber;
        this.citizenCorpBizNumberPartialInfo = citizenCorpBizNumberPartialInfo;
        this.age = age;
        this.customerTypeCode = customerTypeCode;
        this.customerDetailTypeCode = customerDetailTypeCode;
        this.accountTypeCode = accountTypeCode;
    }
    
    // Getters and Setters
    
    public String getCustomerNumber() {
        return customerNumber;
    }
    
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getCitizenCorpBizSerialNumber() {
        return citizenCorpBizSerialNumber;
    }
    
    public void setCitizenCorpBizSerialNumber(String citizenCorpBizSerialNumber) {
        this.citizenCorpBizSerialNumber = citizenCorpBizSerialNumber;
    }
    
    public String getCitizenCorpBizNumberPartialInfo() {
        return citizenCorpBizNumberPartialInfo;
    }
    
    public void setCitizenCorpBizNumberPartialInfo(String citizenCorpBizNumberPartialInfo) {
        this.citizenCorpBizNumberPartialInfo = citizenCorpBizNumberPartialInfo;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getCustomerTypeCode() {
        return customerTypeCode;
    }
    
    public void setCustomerTypeCode(String customerTypeCode) {
        this.customerTypeCode = customerTypeCode;
    }
    
    public String getCustomerDetailTypeCode() {
        return customerDetailTypeCode;
    }
    
    public void setCustomerDetailTypeCode(String customerDetailTypeCode) {
        this.customerDetailTypeCode = customerDetailTypeCode;
    }
    
    public String getAccountTypeCode() {
        return accountTypeCode;
    }
    
    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }
    
    // Builder
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String customerNumber;
        private String accountNumber;
        private String citizenCorpBizSerialNumber;
        private String citizenCorpBizNumberPartialInfo;
        private Integer age;
        private String customerTypeCode;
        private String customerDetailTypeCode;
        private String accountTypeCode;
        
        private Builder() {
        }
        
        public Builder customerNumber(String customerNumber) {
            this.customerNumber = customerNumber;
            return this;
        }
        
        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }
        
        public Builder citizenCorpBizSerialNumber(String citizenCorpBizSerialNumber) {
            this.citizenCorpBizSerialNumber = citizenCorpBizSerialNumber;
            return this;
        }
        
        public Builder citizenCorpBizNumberPartialInfo(String citizenCorpBizNumberPartialInfo) {
            this.citizenCorpBizNumberPartialInfo = citizenCorpBizNumberPartialInfo;
            return this;
        }
        
        public Builder age(Integer age) {
            this.age = age;
            return this;
        }
        
        public Builder customerTypeCode(String customerTypeCode) {
            this.customerTypeCode = customerTypeCode;
            return this;
        }
        
        public Builder customerDetailTypeCode(String customerDetailTypeCode) {
            this.customerDetailTypeCode = customerDetailTypeCode;
            return this;
        }
        
        public Builder accountTypeCode(String accountTypeCode) {
            this.accountTypeCode = accountTypeCode;
            return this;
        }
        
        public CustomerInfo build() {
            return new CustomerInfo(customerNumber, accountNumber,
                    citizenCorpBizSerialNumber, citizenCorpBizNumberPartialInfo,
                    age, customerTypeCode, customerDetailTypeCode, accountTypeCode);
        }
    }
    
    // equals, hashCode, toString
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerInfo that = (CustomerInfo) o;
        return Objects.equals(customerNumber, that.customerNumber) &&
                Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(citizenCorpBizSerialNumber, that.citizenCorpBizSerialNumber) &&
                Objects.equals(citizenCorpBizNumberPartialInfo, that.citizenCorpBizNumberPartialInfo) &&
                Objects.equals(age, that.age) &&
                Objects.equals(customerTypeCode, that.customerTypeCode) &&
                Objects.equals(customerDetailTypeCode, that.customerDetailTypeCode) &&
                Objects.equals(accountTypeCode, that.accountTypeCode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(customerNumber, accountNumber, citizenCorpBizSerialNumber,
                citizenCorpBizNumberPartialInfo, age, customerTypeCode,
                customerDetailTypeCode, accountTypeCode);
    }
    
    @Override
    public String toString() {
        return "CustomerInfo{" +
                "customerNumber='" + customerNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", citizenCorpBizSerialNumber='" + citizenCorpBizSerialNumber + '\'' +
                ", citizenCorpBizNumberPartialInfo='" + citizenCorpBizNumberPartialInfo + '\'' +
                ", age=" + age +
                ", customerTypeCode='" + customerTypeCode + '\'' +
                ", customerDetailTypeCode='" + customerDetailTypeCode + '\'' +
                ", accountTypeCode='" + accountTypeCode + '\'' +
                '}';
    }
}

// Made with Bob
