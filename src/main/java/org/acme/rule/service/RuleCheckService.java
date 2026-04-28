package org.acme.rule.service;

import org.acme.rule.dto.ProcessingResult;
import org.acme.rule.dto.RatePlanChangeRequest;
import org.acme.runtime.ProcessingContext;
import org.acme.runtime.Severity;
import org.acme.runtime.ValidationError;
import org.kie.kogito.Model;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;
import org.kie.kogito.process.Processes;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 요청을 받아 Parent BPMN 을 시동하고 결과를 변환.
 *
 * <p>흐름:
 * <ol>
 *   <li>요청에서 빈 ProcessingContext 생성 (custService/eqpModel 미채움)</li>
 *   <li>Parent BPMN ({@code G1_RatePlanChange}) 시동</li>
 *   <li>Parent 안 ServiceTask 가 {@link FactPrefetchService#populate} 호출하여 ctx 채움</li>
 *   <li>Parent 안 CallActivity 가 {@code PGM_ID_100_validation} 호출 (D295~D525 평가)</li>
 *   <li>Parent process variable 에서 result295~525 Map 추출 → ctx.errors 누적</li>
 *   <li>ProcessingResult 반환</li>
 * </ol>
 */
@Service
@Profile("!fact-server")
public class RuleCheckService {

    static final String PROCESS_ID = "G1_RatePlanChange";
    static final String CTX_VAR = "ctx";
    static final List<String> RESULT_VARS = List.of(
            "result295", "result296", "result298", "result299", "result525");

    private final Processes processes;

    public RuleCheckService(Processes processes) {
        this.processes = processes;
    }

    public ProcessingResult check(RatePlanChangeRequest req) {
        ProcessingContext ctx = new ProcessingContext(req, null, null,
                req != null ? req.externals() : null);
        Map<String, Object> bpmnVariables = runBpmn(ctx);
        accumulateErrors(ctx, bpmnVariables);
        return buildResult(ctx);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Map<String, Object> runBpmn(ProcessingContext ctx) {
        Process process = processes.processById(PROCESS_ID);
        if (process == null) {
            throw new IllegalStateException("BPMN process not found: " + PROCESS_ID);
        }
        Model model = (Model) process.createModel();
        model.fromMap(Map.of(CTX_VAR, ctx));
        ProcessInstance instance = process.createInstance(model);
        instance.start();
        return ((Model) instance.variables()).toMap();
    }

    @SuppressWarnings("unchecked")
    private void accumulateErrors(ProcessingContext ctx, Map<String, Object> vars) {
        for (String key : RESULT_VARS) {
            Object raw = vars.get(key);
            if (!(raw instanceof Map)) {
                continue;
            }
            Map<String, Object> resultMap = (Map<String, Object>) raw;
            String severityStr = (String) resultMap.get("severity");
            if (!"STOP".equals(severityStr)) {
                continue;
            }
            ctx.getErrors().add(new ValidationError(
                    (String) resultMap.get("condDtlTypId"),
                    (String) resultMap.get("errorCode"),
                    (String) resultMap.get("errorMessage"),
                    (String) resultMap.get("ruleName"),
                    Severity.STOP
            ));
        }
    }

    private ProcessingResult buildResult(ProcessingContext ctx) {
        boolean fail = ctx.shouldFastFail();
        String message = "OK";
        if (fail && !ctx.getErrors().isEmpty()) {
            ValidationError first = ctx.getErrors().get(0);
            message = first.errorMessage() != null ? first.errorMessage() : "STOP";
        }
        return new ProcessingResult(
                fail ? "N" : "Y",
                message,
                new ArrayList<>(ctx.getErrors()),
                ctx.getActionPlan()
        );
    }
}
