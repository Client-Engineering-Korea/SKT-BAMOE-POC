package org.acme.rule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.acme.rule.dto.ProcessingResult;
import org.acme.rule.dto.RatePlanChangeRequest;
import org.acme.rule.service.RuleCheckService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rule")
@Profile("!fact-server")
public class RuleCheckController {

    private final RuleCheckService ruleCheckService;

    public RuleCheckController(RuleCheckService ruleCheckService) {
        this.ruleCheckService = ruleCheckService;
    }

    @Operation(summary = "요금제 변경 자격 체크",
            description = "PGM_ID_100 (상품공통기능체크) BPMN 을 시동하여 5단계 (D295/D296/D298/D299/D525) 룰을 평가합니다. "
                    + "svcMgmtNum 으로 fact API 에서 고객/단말 정보를 prefetch 한 뒤, 요청 본문의 변경 정보 + externals 와 결합해 검증합니다.")
    @ApiResponse(responseCode = "200", description = "정상 평가 (processable=Y/N)",
            content = @Content(schema = @Schema(implementation = ProcessingResult.class)))
    @ApiResponse(responseCode = "400", description = "잘못된 요청 (svcMgmtNum 누락 또는 미존재)")
    @PostMapping("/check")
    public ResponseEntity<ProcessingResult> check(@RequestBody RatePlanChangeRequest request) {
        ProcessingResult result = ruleCheckService.check(request);
        return ResponseEntity.ok(result);
    }
}
