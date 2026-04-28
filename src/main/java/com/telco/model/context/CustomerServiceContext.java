package com.telco.model.context;

import com.telco.model.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * 고객 서비스 정보 메인 컨텍스트
 * BAMOE Rule Engine의 입력 데이터 모델
 * 모든 도메인 정보를 집약하여 Rule Engine에 전달
 */
public class CustomerServiceContext implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 계약 정보
     * 고객의 이용 계약 관련 정보
     */
    @Valid
    @NotNull(message = "계약 정보는 필수입니다")
    private ContractInfo contract;
    
    /**
     * 고객 정보
     * 명의 고객의 기본 정보 및 계정 정보
     */
    @Valid
    @NotNull(message = "고객 정보는 필수입니다")
    private CustomerInfo customer;
    
    /**
     * 서비스 정보
     * 서비스 관련 정보 (Rule 1, 7에서 핵심 사용)
     */
    @Valid
    @NotNull(message = "서비스 정보는 필수입니다")
    private ServiceInfo service;
    
    /**
     * 단말기 정보
     * 단말기 관련 정보 (Rule 3, 4, 7에서 사용)
     */
    @Valid
    @NotNull(message = "단말기 정보는 필수입니다")
    private EquipmentInfo equipment;
    
    /**
     * 요금/상품 정보
     * 요금제 및 상품 정보 (Rule 1, 3, 4, 5, 6에서 사용)
     */
    @Valid
    @NotNull(message = "요금/상품 정보는 필수입니다")
    private ProductInfo product;
    
    /**
     * 트랜잭션 컨텍스트
     * API 요청 및 시스템 컨텍스트 정보
     */
    @Valid
    @NotNull(message = "트랜잭션 컨텍스트는 필수입니다")
    private TransactionContext transaction;
    
    // Constructors
    
    /**
     * Default constructor
     */
    public CustomerServiceContext() {
    }
    
    /**
     * All-args constructor
     */
    public CustomerServiceContext(ContractInfo contract, CustomerInfo customer,
                                  ServiceInfo service, EquipmentInfo equipment,
                                  ProductInfo product, TransactionContext transaction) {
        this.contract = contract;
        this.customer = customer;
        this.service = service;
        this.equipment = equipment;
        this.product = product;
        this.transaction = transaction;
    }
    
    // Getters and Setters
    
    public ContractInfo getContract() {
        return contract;
    }
    
    public void setContract(ContractInfo contract) {
        this.contract = contract;
    }
    
    public CustomerInfo getCustomer() {
        return customer;
    }
    
    public void setCustomer(CustomerInfo customer) {
        this.customer = customer;
    }
    
    public ServiceInfo getService() {
        return service;
    }
    
    public void setService(ServiceInfo service) {
        this.service = service;
    }
    
    public EquipmentInfo getEquipment() {
        return equipment;
    }
    
    public void setEquipment(EquipmentInfo equipment) {
        this.equipment = equipment;
    }
    
    public ProductInfo getProduct() {
        return product;
    }
    
    public void setProduct(ProductInfo product) {
        this.product = product;
    }
    
    public TransactionContext getTransaction() {
        return transaction;
    }
    
    public void setTransaction(TransactionContext transaction) {
        this.transaction = transaction;
    }
    
    // Builder
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private ContractInfo contract;
        private CustomerInfo customer;
        private ServiceInfo service;
        private EquipmentInfo equipment;
        private ProductInfo product;
        private TransactionContext transaction;
        
        private Builder() {
        }
        
        public Builder contract(ContractInfo contract) {
            this.contract = contract;
            return this;
        }
        
        public Builder customer(CustomerInfo customer) {
            this.customer = customer;
            return this;
        }
        
        public Builder service(ServiceInfo service) {
            this.service = service;
            return this;
        }
        
        public Builder equipment(EquipmentInfo equipment) {
            this.equipment = equipment;
            return this;
        }
        
        public Builder product(ProductInfo product) {
            this.product = product;
            return this;
        }
        
        public Builder transaction(TransactionContext transaction) {
            this.transaction = transaction;
            return this;
        }
        
        public CustomerServiceContext build() {
            return new CustomerServiceContext(contract, customer, service,
                    equipment, product, transaction);
        }
    }
    
    // equals, hashCode, toString
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerServiceContext that = (CustomerServiceContext) o;
        return Objects.equals(contract, that.contract) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(service, that.service) &&
                Objects.equals(equipment, that.equipment) &&
                Objects.equals(product, that.product) &&
                Objects.equals(transaction, that.transaction);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(contract, customer, service, equipment, product, transaction);
    }
    
    @Override
    public String toString() {
        return "CustomerServiceContext{" +
                "contract=" + contract +
                ", customer=" + customer +
                ", service=" + service +
                ", equipment=" + equipment +
                ", product=" + product +
                ", transaction=" + transaction +
                '}';
    }
}

// Made with Bob
