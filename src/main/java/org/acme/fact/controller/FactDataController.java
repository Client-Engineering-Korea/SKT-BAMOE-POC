package org.acme.fact.controller;

import org.acme.fact.service.FactDataService;
import org.acme.fact.domain.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fact")
@Profile("!rule-server")
public class FactDataController {

    private final FactDataService factDataService;

    public FactDataController(FactDataService factDataService) {
        this.factDataService = factDataService;
    }

    /**
     * 전체 고객 서비스 정보 목록 조회
     * @return
     */
    @Operation(summary = "고객 서비스 목록 조회", description = "고객 서비스 전체 건수와 목록 조회")
    @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustServiceSummary.class))))
    @GetMapping("/cust/listCustService")
    public ListResDto<CustServiceSummary> listCustService() {
        return factDataService.getCustServiceSummaryList();
    }

    /**
     * 고객 서비스 정보 조회
     * @param svcMgmtNum
     * @return
     */
    @Operation(summary = "고객 서비스 정보 조회", description = "서비스관리번호로 고객 서비스 정보 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = CustService.class)))
    @GetMapping("/cust/getCustService/{svcMgmtNum}")
    public ResponseEntity<CustService> getCustService(
            @Parameter(description = "서비스관리번호", example = "7000000001") @PathVariable String svcMgmtNum) {

        // 서비스에서 Map.get(svcMgmtNum) 호출
        CustService info = factDataService.getCustService(svcMgmtNum);
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
    }

    /**
     * 전체 단말 정보 목록 조회
     * @return
     */
    @Operation(summary = "단말 요약 목록 조회", description = "단말 전체 건수와 요약 목록 반환")
    @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = EquipmentModelSummary.class))))
    @GetMapping("/eqp/listEquipmentModel")
    public ListResDto<EquipmentModelSummary> listEquipmentModel() {
        return factDataService.getEquipmentModelSummaryList();
    }

    /**
     * 특정 단말 모델 상세 조회
     * @param eqpMdlCd
     * @return
     */
    @Operation(summary = "특정 단말 모델 상세 조회", description = "모델 코드로 상세 정보를 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = EquipmentModel.class)))
    @GetMapping("/eqp/getEquipmentModel/{eqpMdlCd}")
    public ResponseEntity<EquipmentModel> getEquipmentModel(
            @Parameter(description = "단말 모델 코드", example = "A3D6") @PathVariable String eqpMdlCd) {

        EquipmentModel info = factDataService.getEquipmentModel(eqpMdlCd);
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
    }

    /**
     * 단말 모델 내 특정 라인업 아이템 조회
     * @param eqpMdlCd
     * @param lnupItmCd
     * @return
     */
    @Operation(summary = "단말 모델 내 특정 라인업 아이템 조회", description = "모델 코드와 라인업 아이템 코드로 상세 정보 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = EquipmentModel.EquipmentModelLineup.class)))
    @GetMapping("/eqp/getEquipmentModel/{eqpMdlCd}/items/{lnupItmCd}")
    public ResponseEntity<EquipmentModel.EquipmentModelLineup> getEquipmentModelItem(
            @Parameter(description = "단말 모델 코드", example = "A3D6") @PathVariable String eqpMdlCd,
            @Parameter(description = "라인업 아이템 코드", example = "539") @PathVariable String lnupItmCd) {

        EquipmentModel info = factDataService.getEquipmentModel(eqpMdlCd);
        if (info != null && info.eqpMdlLnupMap().containsKey(lnupItmCd)) {
            return ResponseEntity.ok(info.eqpMdlLnupMap().get(lnupItmCd));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 전체 구성항목값 정보 목록 조회
     * @return
     */
    @Operation(summary = "구성항목값 목록 조회", description = "구성항목값 전체 건수와 목록 조회")
    @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsItemValueSummary.class))))
    @GetMapping("/consItem/listConsItemValue")
    public ListResDto<ConsItemValueSummary> listConsItemValue() {
        return factDataService.getConsItemValueSummaryList();
    }

    /**
     * 구성항목값 정보 조회
     * @param consItmId
     * @return
     */
    @Operation(summary = "구성항목값 정보 조회", description = "구성항목ID로 구성항목값 정보 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = ConsItemValue.class)))
    @GetMapping("/consItem/getConsItemValue/{consItmId}")
    public ResponseEntity<ConsItemValue> getConsItemValue(
            @Parameter(description = "구성항목ID", example = "ITM0000022") @PathVariable String consItmId) {

        ConsItemValue info = factDataService.getConsItemValue(consItmId);
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
    }

    @GetMapping("/mock")
    public boolean mock(@RequestParam(value = "result", required = false) String result) {
        return !"false".equals(result);
    }

}
