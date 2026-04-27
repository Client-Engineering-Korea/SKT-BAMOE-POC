package org.acme.fact.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.fact.domain.*;
import org.acme.fact.domain.CustSubscribe.CustProd;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FactDataService {

    @Value("${fact.excelFilename}")
    private String factDataExcelFilename;

    private Map<String, CustSubscribe> custSubscribeMap = new HashMap<>();
    private Map<String, EquipmentModel> equipmentModelMap = new HashMap<>();

    /**
     * 초기화
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        try {
            log.info("Initializing FactData Service...");
            log.info("Loading Fact data");
            loadFactData();
        } catch (Exception e) {
            log.error("FactData Service Initialization Failed!", e);
            throw e;
        }
    }

    /**
     * Fact data load
     * @throws Exception
     */
    public void loadFactData() throws Exception {
        ClassPathResource resource = new ClassPathResource(factDataExcelFilename);

        try (InputStream is = resource.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            // 고객상품 시트
            Map<String, List<CustProd>> custProdData = loadCustProd(workbook.getSheet("cust_prod"));

            // 고객가입 시트
            Sheet custSubscribeSheet = workbook.getSheet("cust_scrb");
            this.custSubscribeMap = loadCustSubscribe(custSubscribeSheet, custProdData);

            // 단말모델 시트
            Sheet equipmentModelSheet = workbook.getSheet("eqp_mdl");
            this.equipmentModelMap = loadEquipmentModel(equipmentModelSheet);
        }
    }

    /**
     * 고객 상품 정보 load
     * @param sheet
     * @return
     */
    private Map<String, List<CustProd>> loadCustProd(Sheet sheet) {
        Map<String, List<CustProd>> dataMap = new HashMap<>();
        DataFormatter df = new DataFormatter();

        Map<String, Integer> colMap = getColIndex(sheet);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String svcMgmtNum = df.formatCellValue(row.getCell(colMap.get("SVC_MGMT_NUM")));
            CustProd prod = new CustProd(
                    df.formatCellValue(row.getCell(colMap.get("PROD_TYP_CD"))),
                    df.formatCellValue(row.getCell(colMap.get("PROD_ID"))),
                    df.formatCellValue(row.getCell(colMap.get("PROD_NM")))
            );

            // 동일한 svcMgmtNum 별로 리스트에 추가
            dataMap.computeIfAbsent(svcMgmtNum, k -> new ArrayList<>()).add(prod);
        }

        // 메모리 내용 출력
        log.info("===== custProdMap({}) =====", dataMap.size());
        dataMap.forEach((key, value) -> {
            log.info("KEY[{}] : VALUE({})[{}]", key, value.size(), value);
        });
        log.debug("==========");

        return dataMap;
    }

    /**
     * 고객 가입 정보 load
     * @param sheet
     * @param custProdMap
     * @return
     */
    private Map<String, CustSubscribe> loadCustSubscribe(Sheet sheet, Map<String, List<CustProd>> custProdMap) {
        Map<String, CustSubscribe> tempMap = new HashMap<>();
        DataFormatter df = new DataFormatter();

        Map<String, Integer> colMap = getColIndex(sheet);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String svcMgmtNum = df.formatCellValue(row.getCell(colMap.get("SVC_MGMT_NUM"))); // 서비스관리번호

            // 해당 고객의 상품 리스트를 가져옴 (없으면 빈 리스트)
            List<CustProd> prodList = custProdMap.getOrDefault(svcMgmtNum, Collections.emptyList());

            CustSubscribe info = new CustSubscribe(
                    svcMgmtNum,
                    df.formatCellValue(row.getCell(colMap.get("SVC_NUM"))),
                    df.formatCellValue(row.getCell(colMap.get("FEE_PROD_ID"))),
                    df.formatCellValue(row.getCell(colMap.get("SVC_TYP_CD"))),
                    df.formatCellValue(row.getCell(colMap.get("SVC_DTL_CL_CD"))),
                    df.formatCellValue(row.getCell(colMap.get("BIRTH_DT"))),
                    df.formatCellValue(row.getCell(colMap.get("CUST_TYP_CD"))),
                    df.formatCellValue(row.getCell(colMap.get("CUST_DTL_TYP_CD"))),
                    df.formatCellValue(row.getCell(colMap.get("ADD_PROD_CNT"))),
                    df.formatCellValue(row.getCell(colMap.get("EQP_MDL_CD"))),
                    df.formatCellValue(row.getCell(colMap.get("SVC_SCBR_DT"))),
                    df.formatCellValue(row.getCell(colMap.get("SVC_TERM_DT"))),
                    prodList
            );
            tempMap.put(svcMgmtNum, info);
        }

        // 메모리 내용 출력
        log.info("===== custSubscribeMap({}) =====", tempMap.size());
        tempMap.forEach((key, value) -> {
            log.info("KEY[{}] : VALUE[{}]", key, value);
        });
        log.debug("==========");

        return tempMap;
    }

    /**
     * 단말 모델 정보 load
     * @param sheet
     * @return
     */
    private Map<String, EquipmentModel> loadEquipmentModel(Sheet sheet) {
        // 1. 임시 그룹화 맵 (Key: eqpMdlCd, Value: 해당 모델의 상세 정보들)
        // 나중에 레코드 생성을 위해 임시로 데이터를 모읍니다.
        Map<String, Map<String, EquipmentModel.EquipmentModelLineup>> groupMap = new HashMap<>();
        Map<String, String[]> modelBasicInfo = new HashMap<>(); // 모델명, 버전 저장용

        DataFormatter df = new DataFormatter();

        Map<String, Integer> colMap = getColIndex(sheet);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String mdlCd = df.formatCellValue(row.getCell(colMap.get("EQP_MDL_CD")));
            String mdlNm = df.formatCellValue(row.getCell(colMap.get("EQP_MDL_NM")));
            String ver = df.formatCellValue(row.getCell(colMap.get("VER_NUM")));
            String itmCd = df.formatCellValue(row.getCell(colMap.get("LNUP_ITM_CD")));

            // 모델 기본 정보 보관 (한 번만 하면 되지만 그룹화를 위해 저장)
            modelBasicInfo.putIfAbsent(mdlCd, new String[]{mdlNm, ver});

            // 상세 정보 객체 생성
            EquipmentModel.EquipmentModelLineup lnup = new EquipmentModel.EquipmentModelLineup(
                    itmCd,
                    df.formatCellValue(row.getCell(colMap.get("LNUP_ITM_NM"))),
                    df.formatCellValue(row.getCell(colMap.get("LNUP_DTL_ITM_CD"))),
                    df.formatCellValue(row.getCell(colMap.get("LNUP_DTL_ITM_NM")))
            );

            // [핵심] 모델별 Map에 itmCd를 키로 담기
            groupMap.computeIfAbsent(mdlCd, k -> new HashMap<>()).put(itmCd, lnup);
        }

        // 2. 최종 EquipmentModel 레코드 생성
        Map<String, EquipmentModel> finalMap = new HashMap<>();
        groupMap.forEach((mdlCd, dtlMap) -> {
            String[] basic = modelBasicInfo.get(mdlCd);
            finalMap.put(mdlCd, new EquipmentModel(
                    mdlCd,
                    basic[0], // eqpMdlNm
                    basic[1], // verNum
                    Collections.unmodifiableMap(dtlMap) // 불변 맵으로 주입
            ));
        });

        // 메모리 내용 출력
        log.info("===== finalMap({}) =====", finalMap.size());
        finalMap.forEach((key, value) -> {
            log.info("KEY[{}] : VALUE({})[{}]", key, value.eqpMdlLnupMap().size(), value);
        });
        log.debug("==========");

        return finalMap;
    }

    /**
     * 엑셀 컬럼 index 생성 반환
     * @param sheet   엑셀시트
     * @return 컬럼MAP
     */
    private Map<String, Integer> getColIndex(Sheet sheet) {
        Row headerRow = sheet.getRow(0);
        Map<String, Integer> colMap = new HashMap<>();
        for (Cell cell : headerRow) {
            colMap.put(cell.getStringCellValue(), cell.getColumnIndex());
        }
        log.info("=== colMap:{}", colMap);
        log.info("=== getLastRowNum:{}", sheet.getLastRowNum());
        return colMap;
    }

    /**
     * 고객 가입 요약 정보 목록 및 건수 조회
     * @return
     */
    public ListResDto<CustSubscribeSummary> getCustSummaryList() {
        List<CustSubscribeSummary> list = custSubscribeMap.values().stream()
                .map(c -> new CustSubscribeSummary(c.svcMgmtNum(), c.svcNum()))
                .toList();
        return new ListResDto<>(list.size(), list);
    }

    /**
     * 고객 가입 정보 조회
     * @param svcMgmtNum
     * @return
     */
    public CustSubscribe getCustSubscribe(String svcMgmtNum) {
        return custSubscribeMap.get(svcMgmtNum);
    }

    /**
     * 단말 모델 요약 정보 목록 및 건수 조회
     * @return
     */
    public ListResDto<EquipmentModelSummary> getEquipmentModelSummaryList() {
        List<EquipmentModelSummary> list = equipmentModelMap.values().stream()
                .map(e -> new EquipmentModelSummary(e.eqpMdlCd(), e.eqpMdlNm()))
                .toList();

        return new ListResDto<>(list.size(), list);
    }

    /**
     * 단말 모델 정보 조회
     * @param eqpMdlCd
     * @return
     */
    public EquipmentModel getEquipmentModel(String eqpMdlCd) {
        return equipmentModelMap.get(eqpMdlCd);
    }
}
