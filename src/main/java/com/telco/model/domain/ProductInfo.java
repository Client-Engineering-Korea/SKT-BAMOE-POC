package com.telco.model.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 요금/상품 정보
 * Rule 1, 3, 4, 5, 6에서 핵심적으로 사용되는 요금제 및 상품 정보
 */
public class ProductInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 요금상품id
     * Rule 1, 3, 4, 5, 6에서 핵심적으로 사용
     */
    @NotNull(message = "요금상품id는 필수입니다")
    @Size(max = 20)
    private String feeProductId;
    
    /**
     * 서비스별상품id
     * 상품 식별자
     */
    @NotNull(message = "서비스별상품id는 필수입니다")
    @Size(max = 20)
    private String productId;
    
    /**
     * 상품가입일자
     * 상품 가입 날짜
     */
    @NotNull(message = "상품가입일자는 필수입니다")
    @Past
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate subscribeDate;
    
    /**
     * 상품해지사유코드
     * 상품 해지 사유
     */
    @Size(max = 2)
    private String productTerminationReasonCode;
    
    /**
     * 납부방법코드
     * 결제 수단 코드
     */
    @NotNull(message = "납부방법코드는 필수입니다")
    @Size(min = 2, max = 2)
    private String paymentMethodCode;
    
    /**
     * 상품사용여부
     * Rule 1에서 사용 - 상품의 사용 가능 상태를 나타내는 플래그
     * Y: 사용 가능, N: 사용 불가
     */
    @Size(min = 1, max = 1)
    @Pattern(regexp = "[YN]", message = "상품사용여부는 Y 또는 N이어야 합니다")
    private String productUsageFlag;
    
    // Constructors
    
    /**
     * Default constructor
     */
    public ProductInfo() {
    }
    
    /**
     * All-args constructor
     */
    public ProductInfo(String feeProductId, String productId, LocalDate subscribeDate,
                      String productTerminationReasonCode, String paymentMethodCode,
                      String productUsageFlag) {
        this.feeProductId = feeProductId;
        this.productId = productId;
        this.subscribeDate = subscribeDate;
        this.productTerminationReasonCode = productTerminationReasonCode;
        this.paymentMethodCode = paymentMethodCode;
        this.productUsageFlag = productUsageFlag;
    }
    
    // Getters and Setters
    
    public String getFeeProductId() {
        return feeProductId;
    }
    
    public void setFeeProductId(String feeProductId) {
        this.feeProductId = feeProductId;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public LocalDate getSubscribeDate() {
        return subscribeDate;
    }
    
    public void setSubscribeDate(LocalDate subscribeDate) {
        this.subscribeDate = subscribeDate;
    }
    
    public String getProductTerminationReasonCode() {
        return productTerminationReasonCode;
    }
    
    public void setProductTerminationReasonCode(String productTerminationReasonCode) {
        this.productTerminationReasonCode = productTerminationReasonCode;
    }
    
    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }
    
    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }
    
    public String getProductUsageFlag() {
        return productUsageFlag;
    }
    
    public void setProductUsageFlag(String productUsageFlag) {
        this.productUsageFlag = productUsageFlag;
    }
    
    // Builder
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String feeProductId;
        private String productId;
        private LocalDate subscribeDate;
        private String productTerminationReasonCode;
        private String paymentMethodCode;
        private String productUsageFlag;
        
        private Builder() {
        }
        
        public Builder feeProductId(String feeProductId) {
            this.feeProductId = feeProductId;
            return this;
        }
        
        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }
        
        public Builder subscribeDate(LocalDate subscribeDate) {
            this.subscribeDate = subscribeDate;
            return this;
        }
        
        public Builder productTerminationReasonCode(String productTerminationReasonCode) {
            this.productTerminationReasonCode = productTerminationReasonCode;
            return this;
        }
        
        public Builder paymentMethodCode(String paymentMethodCode) {
            this.paymentMethodCode = paymentMethodCode;
            return this;
        }
        
        public Builder productUsageFlag(String productUsageFlag) {
            this.productUsageFlag = productUsageFlag;
            return this;
        }
        
        public ProductInfo build() {
            return new ProductInfo(feeProductId, productId, subscribeDate,
                    productTerminationReasonCode, paymentMethodCode, productUsageFlag);
        }
    }
    
    // equals, hashCode, toString
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfo that = (ProductInfo) o;
        return Objects.equals(feeProductId, that.feeProductId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(subscribeDate, that.subscribeDate) &&
                Objects.equals(productTerminationReasonCode, that.productTerminationReasonCode) &&
                Objects.equals(paymentMethodCode, that.paymentMethodCode) &&
                Objects.equals(productUsageFlag, that.productUsageFlag);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(feeProductId, productId, subscribeDate,
                productTerminationReasonCode, paymentMethodCode, productUsageFlag);
    }
    
    @Override
    public String toString() {
        return "ProductInfo{" +
                "feeProductId='" + feeProductId + '\'' +
                ", productId='" + productId + '\'' +
                ", subscribeDate=" + subscribeDate +
                ", productTerminationReasonCode='" + productTerminationReasonCode + '\'' +
                ", paymentMethodCode='" + paymentMethodCode + '\'' +
                ", productUsageFlag='" + productUsageFlag + '\'' +
                '}';
    }
}

// Made with Bob
