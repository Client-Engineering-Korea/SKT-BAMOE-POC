package com.telco.model.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 단말기 정보
 * Rule 3, 4, 7에서 사용되는 단말기 관련 정보
 */
public class EquipmentInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 단말기모델코드
     * Rule 3, 4, 7에서 핵심적으로 사용
     */
    @NotNull(message = "단말기모델코드는 필수입니다")
    @Size(min = 4, max = 4)
    private String equipmentModelCode;
    
    /**
     * 단말기일련번호
     * IMEI 등 단말기 고유 번호
     */
    @NotNull(message = "단말기일련번호는 필수입니다")
    @Size(max = 20)
    private String equipmentSerialNumber;
    
    /**
     * SIM일련번호
     * SIM 카드 일련번호
     */
    @Size(max = 20)
    private String simSerialNumber;
    
    /**
     * 단말기용도코드
     * Rule 4, 7에서 사용 (W=웨어러블)
     */
    @NotNull(message = "단말기용도코드는 필수입니다")
    @Size(min = 1, max = 1)
    private String equipmentUsageCode;
    
    /**
     * 단말기방식코드
     * 통신 방식 코드
     */
    @Size(max = 1)
    private String equipmentMethodCode;
    
    /**
     * 단말기버전번호
     * 단말기 버전 정보
     */
    @Size(max = 10)
    private String equipmentVersionNumber;
    
    /**
     * 단말기관리상태코드
     * 단말기의 관리 상태
     */
    @Size(max = 2)
    private String equipmentManagementStatusCode;
    
    /**
     * 단말기출시일자
     * 단말기 출시 날짜
     */
    @Past
    private LocalDate marketingDate;
    
    /**
     * network방식코드
     * 네트워크 타입 코드
     */
    @Size(max = 2)
    private String networkMethodCode;
    
    /**
     * NATE구분코드
     * NATE 서비스 구분
     */
    @Size(max = 2)
    private String nateClassCode;
    
    /**
     * 킷모델코드
     * 킷 모델 정보
     */
    @Size(max = 10)
    private String kitModelCode;
    
    /**
     * 킷일련번호
     * 킷 일련번호
     */
    @Size(max = 20)
    private String kitSerialNumber;
    
    // Constructors
    
    /**
     * Default constructor
     */
    public EquipmentInfo() {
    }
    
    /**
     * All-args constructor
     */
    public EquipmentInfo(String equipmentModelCode, String equipmentSerialNumber,
                        String simSerialNumber, String equipmentUsageCode,
                        String equipmentMethodCode, String equipmentVersionNumber,
                        String equipmentManagementStatusCode, LocalDate marketingDate,
                        String networkMethodCode, String nateClassCode,
                        String kitModelCode, String kitSerialNumber) {
        this.equipmentModelCode = equipmentModelCode;
        this.equipmentSerialNumber = equipmentSerialNumber;
        this.simSerialNumber = simSerialNumber;
        this.equipmentUsageCode = equipmentUsageCode;
        this.equipmentMethodCode = equipmentMethodCode;
        this.equipmentVersionNumber = equipmentVersionNumber;
        this.equipmentManagementStatusCode = equipmentManagementStatusCode;
        this.marketingDate = marketingDate;
        this.networkMethodCode = networkMethodCode;
        this.nateClassCode = nateClassCode;
        this.kitModelCode = kitModelCode;
        this.kitSerialNumber = kitSerialNumber;
    }
    
    // Getters and Setters
    
    public String getEquipmentModelCode() {
        return equipmentModelCode;
    }
    
    public void setEquipmentModelCode(String equipmentModelCode) {
        this.equipmentModelCode = equipmentModelCode;
    }
    
    public String getEquipmentSerialNumber() {
        return equipmentSerialNumber;
    }
    
    public void setEquipmentSerialNumber(String equipmentSerialNumber) {
        this.equipmentSerialNumber = equipmentSerialNumber;
    }
    
    public String getSimSerialNumber() {
        return simSerialNumber;
    }
    
    public void setSimSerialNumber(String simSerialNumber) {
        this.simSerialNumber = simSerialNumber;
    }
    
    public String getEquipmentUsageCode() {
        return equipmentUsageCode;
    }
    
    public void setEquipmentUsageCode(String equipmentUsageCode) {
        this.equipmentUsageCode = equipmentUsageCode;
    }
    
    public String getEquipmentMethodCode() {
        return equipmentMethodCode;
    }
    
    public void setEquipmentMethodCode(String equipmentMethodCode) {
        this.equipmentMethodCode = equipmentMethodCode;
    }
    
    public String getEquipmentVersionNumber() {
        return equipmentVersionNumber;
    }
    
    public void setEquipmentVersionNumber(String equipmentVersionNumber) {
        this.equipmentVersionNumber = equipmentVersionNumber;
    }
    
    public String getEquipmentManagementStatusCode() {
        return equipmentManagementStatusCode;
    }
    
    public void setEquipmentManagementStatusCode(String equipmentManagementStatusCode) {
        this.equipmentManagementStatusCode = equipmentManagementStatusCode;
    }
    
    public LocalDate getMarketingDate() {
        return marketingDate;
    }
    
    public void setMarketingDate(LocalDate marketingDate) {
        this.marketingDate = marketingDate;
    }
    
    public String getNetworkMethodCode() {
        return networkMethodCode;
    }
    
    public void setNetworkMethodCode(String networkMethodCode) {
        this.networkMethodCode = networkMethodCode;
    }
    
    public String getNateClassCode() {
        return nateClassCode;
    }
    
    public void setNateClassCode(String nateClassCode) {
        this.nateClassCode = nateClassCode;
    }
    
    public String getKitModelCode() {
        return kitModelCode;
    }
    
    public void setKitModelCode(String kitModelCode) {
        this.kitModelCode = kitModelCode;
    }
    
    public String getKitSerialNumber() {
        return kitSerialNumber;
    }
    
    public void setKitSerialNumber(String kitSerialNumber) {
        this.kitSerialNumber = kitSerialNumber;
    }
    
    // Builder
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String equipmentModelCode;
        private String equipmentSerialNumber;
        private String simSerialNumber;
        private String equipmentUsageCode;
        private String equipmentMethodCode;
        private String equipmentVersionNumber;
        private String equipmentManagementStatusCode;
        private LocalDate marketingDate;
        private String networkMethodCode;
        private String nateClassCode;
        private String kitModelCode;
        private String kitSerialNumber;
        
        private Builder() {
        }
        
        public Builder equipmentModelCode(String equipmentModelCode) {
            this.equipmentModelCode = equipmentModelCode;
            return this;
        }
        
        public Builder equipmentSerialNumber(String equipmentSerialNumber) {
            this.equipmentSerialNumber = equipmentSerialNumber;
            return this;
        }
        
        public Builder simSerialNumber(String simSerialNumber) {
            this.simSerialNumber = simSerialNumber;
            return this;
        }
        
        public Builder equipmentUsageCode(String equipmentUsageCode) {
            this.equipmentUsageCode = equipmentUsageCode;
            return this;
        }
        
        public Builder equipmentMethodCode(String equipmentMethodCode) {
            this.equipmentMethodCode = equipmentMethodCode;
            return this;
        }
        
        public Builder equipmentVersionNumber(String equipmentVersionNumber) {
            this.equipmentVersionNumber = equipmentVersionNumber;
            return this;
        }
        
        public Builder equipmentManagementStatusCode(String equipmentManagementStatusCode) {
            this.equipmentManagementStatusCode = equipmentManagementStatusCode;
            return this;
        }
        
        public Builder marketingDate(LocalDate marketingDate) {
            this.marketingDate = marketingDate;
            return this;
        }
        
        public Builder networkMethodCode(String networkMethodCode) {
            this.networkMethodCode = networkMethodCode;
            return this;
        }
        
        public Builder nateClassCode(String nateClassCode) {
            this.nateClassCode = nateClassCode;
            return this;
        }
        
        public Builder kitModelCode(String kitModelCode) {
            this.kitModelCode = kitModelCode;
            return this;
        }
        
        public Builder kitSerialNumber(String kitSerialNumber) {
            this.kitSerialNumber = kitSerialNumber;
            return this;
        }
        
        public EquipmentInfo build() {
            return new EquipmentInfo(equipmentModelCode, equipmentSerialNumber,
                    simSerialNumber, equipmentUsageCode, equipmentMethodCode,
                    equipmentVersionNumber, equipmentManagementStatusCode,
                    marketingDate, networkMethodCode, nateClassCode,
                    kitModelCode, kitSerialNumber);
        }
    }
    
    // equals, hashCode, toString
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentInfo that = (EquipmentInfo) o;
        return Objects.equals(equipmentModelCode, that.equipmentModelCode) &&
                Objects.equals(equipmentSerialNumber, that.equipmentSerialNumber) &&
                Objects.equals(simSerialNumber, that.simSerialNumber) &&
                Objects.equals(equipmentUsageCode, that.equipmentUsageCode) &&
                Objects.equals(equipmentMethodCode, that.equipmentMethodCode) &&
                Objects.equals(equipmentVersionNumber, that.equipmentVersionNumber) &&
                Objects.equals(equipmentManagementStatusCode, that.equipmentManagementStatusCode) &&
                Objects.equals(marketingDate, that.marketingDate) &&
                Objects.equals(networkMethodCode, that.networkMethodCode) &&
                Objects.equals(nateClassCode, that.nateClassCode) &&
                Objects.equals(kitModelCode, that.kitModelCode) &&
                Objects.equals(kitSerialNumber, that.kitSerialNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(equipmentModelCode, equipmentSerialNumber, simSerialNumber,
                equipmentUsageCode, equipmentMethodCode, equipmentVersionNumber,
                equipmentManagementStatusCode, marketingDate, networkMethodCode,
                nateClassCode, kitModelCode, kitSerialNumber);
    }
    
    @Override
    public String toString() {
        return "EquipmentInfo{" +
                "equipmentModelCode='" + equipmentModelCode + '\'' +
                ", equipmentSerialNumber='" + equipmentSerialNumber + '\'' +
                ", simSerialNumber='" + simSerialNumber + '\'' +
                ", equipmentUsageCode='" + equipmentUsageCode + '\'' +
                ", equipmentMethodCode='" + equipmentMethodCode + '\'' +
                ", equipmentVersionNumber='" + equipmentVersionNumber + '\'' +
                ", equipmentManagementStatusCode='" + equipmentManagementStatusCode + '\'' +
                ", marketingDate=" + marketingDate +
                ", networkMethodCode='" + networkMethodCode + '\'' +
                ", nateClassCode='" + nateClassCode + '\'' +
                ", kitModelCode='" + kitModelCode + '\'' +
                ", kitSerialNumber='" + kitSerialNumber + '\'' +
                '}';
    }
}

// Made with Bob
