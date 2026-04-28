package org.acme.runtime;

import org.acme.fact.domain.CustService;
import org.acme.fact.domain.EquipmentModel;
import org.acme.rule.dto.Externals;
import org.acme.rule.dto.RatePlanChangeRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * BPMN PGM_ID_100_validation 의 단일 process variable.
 *
 * <p>BPMN/DMN 으로 들어가는 fact 의 단일 진실의 원천. 클라이언트 요청(request)과
 * fact API 결과(custService, eqpModel) 와 외부 시스템 결과(externals) 를 한 봉투에 묶음.
 *
 * <p>errors 와 actionPlan 은 BPMN 진행 중 누적되며 fast-fail 판정에 사용됨.
 */
public class ProcessingContext implements Serializable {

    private RatePlanChangeRequest request;
    private CustService custService;
    private EquipmentModel eqpModel;
    private Externals externals;
    private final List<ValidationError> errors = new ArrayList<>();
    private final ActionPlan actionPlan = new ActionPlan();

    public ProcessingContext() {}

    public ProcessingContext(RatePlanChangeRequest request, CustService custService,
                             EquipmentModel eqpModel, Externals externals) {
        this.request = request;
        this.custService = custService;
        this.eqpModel = eqpModel;
        this.externals = externals;
    }

    public RatePlanChangeRequest getRequest() { return request; }
    public void setRequest(RatePlanChangeRequest request) { this.request = request; }

    public CustService getCustService() { return custService; }
    public void setCustService(CustService custService) { this.custService = custService; }

    public EquipmentModel getEqpModel() { return eqpModel; }
    public void setEqpModel(EquipmentModel eqpModel) { this.eqpModel = eqpModel; }

    public Externals getExternals() { return externals; }
    public void setExternals(Externals externals) { this.externals = externals; }

    public List<ValidationError> getErrors() { return errors; }
    public ActionPlan getActionPlan() { return actionPlan; }

    /**
     * STOP 또는 WARN severity 가 하나라도 있으면 fast-fail.
     * 모든 fast-fail 판정의 single source of truth.
     */
    public boolean shouldFastFail() {
        return errors.stream()
                .anyMatch(e -> e.severity() == Severity.STOP || e.severity() == Severity.WARN);
    }
}
