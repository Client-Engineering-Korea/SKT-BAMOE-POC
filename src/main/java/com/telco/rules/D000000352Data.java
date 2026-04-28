package com.telco.rules;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

import com.telco.model.context.TransactionContext;
import com.telco.model.result.ValidationError;

/**
 * D000000352 - WiBro통합조절요금적용 룰을 위한 RuleUnitData
 * 
 * 이 클래스는 WiBro 통합조절요금 적용 여부를 검증하는 룰의 데이터 컨테이너입니다.
 * 외부 모듈(zordms0360100.f_ordrs03f0000754_check) 호출 결과를 기반으로 검증합니다.
 * 
 * COND_DTL_TYP_ID: D000000352
 * COND_CONS_ID: CONDCP000003094
 */
public class D000000352Data implements RuleUnitData {

    private final DataStore<TransactionContext> transactions = DataSource.createStore();

    private final DataStore<ValidationError> errors = DataSource.createStore();

    public DataStore<TransactionContext> getTransactions() {
        return transactions;
    }

    public DataStore<ValidationError> getErrors() {
        return errors;
    }
}

// Made with Bob
