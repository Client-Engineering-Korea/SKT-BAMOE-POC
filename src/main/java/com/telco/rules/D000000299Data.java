package com.telco.rules;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

import com.telco.model.context.TransactionContext;
import com.telco.model.result.ValidationError;

public class D000000299Data implements RuleUnitData {

    private final DataStore<TransactionContext> transactions = DataSource.createStore();

    private final DataStore<ValidationError> errors = DataSource.createStore();

    public DataStore<TransactionContext> getTransactions() {
        return transactions;
    }

    public DataStore<ValidationError> getErrors() {
        return errors;
    }
}
