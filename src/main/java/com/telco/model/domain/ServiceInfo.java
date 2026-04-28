package com.telco.model.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
// import java.time.LocalDate;
import java.util.Objects;

/**
 * 서비스 정보
 * Rule 1, 7에서 핵심적으로 사용되는 서비스 관련 정보
 */
public class ServiceInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 이용종류코드
     * - Rule 1: 처리불가이용종류체크_부가상품 
     *   + 적용 시퀀스 :: SEQ01 | SEQ02 | SEQ03 | SEQ04
     */
    @NotNull(message = "이용종류코드는 필수입니다")
    @Size(min = 1, max = 2)
    private String serviceTypeCode;

    /**
     * 로밍이용종류 강제처리권한자 여부 (ORDAU5898)
     *
     * - Rule 1: 처리불가이용종류체크_부가상품
     *  + 적용 시퀀스 :: SEQ01
     */
    @NotNull(message = "로밍이용종류 강제처리권한자 여부는 필수입니다")
    private Boolean hasRoamingForceProcessAuthority = false;

    /**
     * I/B 로밍 상품 사용여부 (IB_ROMING_PROD)
     *
     * - Rule 1: 처리불가이용종류체크_부가상품
     *  + 적용 시퀀스 :: SEQ04
     */
    @Size(max = 1)
    private String ibRoamingProductUsedYn = "Y";

    /**
     * I/B 렌탈로밍 상품 사용여부 (IB_RENT_ROMING_PROD)
     *
     * - Rule 1: 처리불가이용종류체크_부가상품
     *  + 적용 시퀀스 :: SEQ05
     */
    @Size(max = 1)
    private String ibRentRoamingProductUsedYn = "N";

    /**
     * 가입해지구분코드 (값이 "01"일때만 매칭)
     * - Rule 1: 처리불가이용종류체크_부가상품
     *  + 적용 시퀀스 :: SEQ05
     */
    @Size(max = 2)
    private String subscriptionCancelCode;



    // Constructors
    
    /**
     * Default constructor
     */
    public ServiceInfo() {
    }
    
    /**
     * All-args constructor
     */
    public ServiceInfo(String serviceTypeCode, Boolean hasRoamingForceProcessAuthority,
                      String ibRoamingProductUsedYn, String ibRentRoamingProductUsedYn,
                      String subscriptionCancelCode) {
        this.serviceTypeCode = serviceTypeCode;
        this.hasRoamingForceProcessAuthority = hasRoamingForceProcessAuthority;
        this.ibRoamingProductUsedYn = ibRoamingProductUsedYn;
        this.ibRentRoamingProductUsedYn = ibRentRoamingProductUsedYn;
        this.subscriptionCancelCode = subscriptionCancelCode;
    }
    
    // Getters and Setters
    
    public String getServiceTypeCode() {
        return serviceTypeCode;
    }
    
    public void setServiceTypeCode(String serviceTypeCode) {
        this.serviceTypeCode = serviceTypeCode;
    }
    
    public Boolean getHasRoamingForceProcessAuthority() {
        return hasRoamingForceProcessAuthority;
    }

    public Boolean isHasRoamingForceProcessAuthority() {
        return hasRoamingForceProcessAuthority;
    }
    
    public void setHasRoamingForceProcessAuthority(Boolean hasRoamingForceProcessAuthority) {
        this.hasRoamingForceProcessAuthority = hasRoamingForceProcessAuthority;
    }
    
    public String getIbRoamingProductUsedYn() {
        return ibRoamingProductUsedYn;
    }
    
    public void setIbRoamingProductUsedYn(String ibRoamingProductUsedYn) {
        this.ibRoamingProductUsedYn = ibRoamingProductUsedYn;
    }
    
    public String getIbRentRoamingProductUsedYn() {
        return ibRentRoamingProductUsedYn;
    }
    
    public void setIbRentRoamingProductUsedYn(String ibRentRoamingProductUsedYn) {
        this.ibRentRoamingProductUsedYn = ibRentRoamingProductUsedYn;
    }
    
    public String getSubscriptionCancelCode() {
        return subscriptionCancelCode;
    }
    
    public void setSubscriptionCancelCode(String subscriptionCancelCode) {
        this.subscriptionCancelCode = subscriptionCancelCode;
    }
    
    // equals, hashCode, toString
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceInfo that = (ServiceInfo) o;
        return Objects.equals(serviceTypeCode, that.serviceTypeCode) &&
                Objects.equals(hasRoamingForceProcessAuthority, that.hasRoamingForceProcessAuthority) &&
                Objects.equals(ibRoamingProductUsedYn, that.ibRoamingProductUsedYn) &&
                Objects.equals(ibRentRoamingProductUsedYn, that.ibRentRoamingProductUsedYn) &&
                Objects.equals(subscriptionCancelCode, that.subscriptionCancelCode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(serviceTypeCode, hasRoamingForceProcessAuthority,
                ibRoamingProductUsedYn, ibRentRoamingProductUsedYn, subscriptionCancelCode);
    }
    
    @Override
    public String toString() {
        return "ServiceInfo{" +
                "serviceTypeCode='" + serviceTypeCode + '\'' +
                ", hasRoamingForceProcessAuthority=" + hasRoamingForceProcessAuthority +
                ", ibRoamingProductUsedYn='" + ibRoamingProductUsedYn + '\'' +
                ", ibRentRoamingProductUsedYn='" + ibRentRoamingProductUsedYn + '\'' +
                ", subscriptionCancelCode='" + subscriptionCancelCode + '\'' +
                '}';
    }
}

// Made with Bob
