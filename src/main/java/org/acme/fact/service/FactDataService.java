package org.acme.fact.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.fact.domain.*;
import org.acme.fact.domain.CustService.CustProd;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("!rule-server")
public class FactDataService {

    @Value("${fact.excelFilename}")
    private String factDataExcelFilename;

    private Map<String, CustService> custServiceMap = new HashMap<>();          // Map<SVC_MGMT_NUM, CustService>
    private Map<String, EquipmentModel> equipmentModelMap = new HashMap<>();    // Map<EQP_MDL_CD, EquipmentModel>
    private Map<String, ConsItemValue> consItemValueMap = new HashMap<>();      // Map<CONS_ITM_NM, ConsItemValue>

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
            Sheet custServiceSheet = workbook.getSheet("cust_svc");
            loadCustService(custServiceSheet, custProdData);

            // 단말모델 시트
            Sheet equipmentModelSheet = workbook.getSheet("eqp_mdl");
            loadEquipmentModel(equipmentModelSheet);

            // 구성항목값 시트
            Sheet consItemValueSheet = workbook.getSheet("cons_itm_val");
            loadConsItemValue(consItemValueSheet);
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

        return dataMap;
    }

    /**
     * 고객 서비스 정보 load
     * @param sheet
     * @param custProdMap
     * @return
     */
    private void loadCustService(Sheet sheet, Map<String, List<CustProd>> custProdMap) {
        Map<String, CustService> tempMap = new HashMap<>();
        DataFormatter df = new DataFormatter();

        Map<String, Integer> colMap = getColIndex(sheet);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String svcMgmtNum = df.formatCellValue(row.getCell(colMap.get("SVC_MGMT_NUM"))); // 서비스관리번호

            // 해당 고객의 상품 리스트를 가져옴 (없으면 빈 리스트)
            List<CustProd> prodList = custProdMap.getOrDefault(svcMgmtNum, Collections.emptyList());

            CustService info = new CustService(
                    df.formatCellValue(row.getCell(colMap.get("CNTRCT_MGMT_NUM"))),      // 계약관리번호
                    df.formatCellValue(row.getCell(colMap.get("USE_CNTRCT_CL_CD"))),     // 이용계약구분코드
                    df.formatCellValue(row.getCell(colMap.get("USE_CNTRCT_ST_CD"))),     // 이용계약상태코드
                    df.formatCellValue(row.getCell(colMap.get("CNTRCT_DT"))),            // 계약일자
                    df.formatCellValue(row.getCell(colMap.get("CO_CL_CD"))),             // 회사구분코드
                    df.formatCellValue(row.getCell(colMap.get("TAX_BILL_ISUE_YN"))),     // 세금계산서발행여부
                    df.formatCellValue(row.getCell(colMap.get("NM_CUST_NUM"))),          // 명의고객번호
                    df.formatCellValue(row.getCell(colMap.get("ACNT_NUM"))),             // 계정번호
                    svcMgmtNum,
                    df.formatCellValue(row.getCell(colMap.get("SVC_CD"))),               // 서비스구분코드
                    df.formatCellValue(row.getCell(colMap.get("SVC_NUM"))),              // 서비스번호
                    df.formatCellValue(row.getCell(colMap.get("SVC_ST_CD"))),            // 서비스상태코드
                    df.formatCellValue(row.getCell(colMap.get("SVC_ST_CHG_CD"))),        // 서비스상태변경코드
                    df.formatCellValue(row.getCell(colMap.get("SVC_CHG_RSN_CD"))),       // 서비스변경사유코드
                    df.formatCellValue(row.getCell(colMap.get("SVC_TYP_CD"))),           // 서비스이용종류코드
                    df.formatCellValue(row.getCell(colMap.get("SVC_TYP_NM"))),           // 서비스이용종류명
                    df.formatCellValue(row.getCell(colMap.get("SVC_SCRB_DT"))),          // 서비스가입일자
                    df.formatCellValue(row.getCell(colMap.get("SCRB_REQ_RSN_CD"))),      // 가입신청사유코드
                    df.formatCellValue(row.getCell(colMap.get("SVC_TERM_DT"))),          // 서비스해지일자
                    df.formatCellValue(row.getCell(colMap.get("FEE_PROD_ID"))),          // 요금상품id
                    df.formatCellValue(row.getCell(colMap.get("IDNT_NUM_CD"))),          // 식별번호구분코드
                    df.formatCellValue(row.getCell(colMap.get("GRTM_CL_CD"))),           // 보증금구분코드
                    df.formatCellValue(row.getCell(colMap.get("WLF_DC_CD"))),            // 복지할인유형코드
                    df.formatCellValue(row.getCell(colMap.get("WLF_DC_NM"))),            // 복지할인유형명
                    df.formatCellValue(row.getCell(colMap.get("WEB_MBR_SCRB_CL_CD"))),   // 웹회원가입구분코드(TWORLD가입여부)
                    df.formatCellValue(row.getCell(colMap.get("WEB_MBR_REQ_AGREE_YN"))), // 웹회원신청동의여부(TWORLD동의여부)
                    df.formatCellValue(row.getCell(colMap.get("EQP_MDL_CD"))),           // 단말기모델코드
                    df.formatCellValue(row.getCell(colMap.get("EQP_SER_NUM"))),          // 단말기일련번호
                    df.formatCellValue(row.getCell(colMap.get("SIM_SER_NUM"))),          // sim일련번호
                    df.formatCellValue(row.getCell(colMap.get("EQP_USG_CD"))),           // 단말기용도코드
                    df.formatCellValue(row.getCell(colMap.get("EQP_MTHD_CD"))),          // 단말기방식코드
                    df.formatCellValue(row.getCell(colMap.get("REL_SVC_MGMT_NUM"))),     // 관계서비스관리번호
                    df.formatCellValue(row.getCell(colMap.get("REL_FEE_PROD_ID"))),      // 관계서비스요금제
                    df.formatCellValue(row.getCell(colMap.get("CTZ_CORP_BIZ_SER_NUM"))), // 주민법인사업자등록일련번호
                    df.formatCellValue(row.getCell(colMap.get("CTZ_CORP_BIZ_NUM_PINF"))),// 주민법인사업자등록번호부분정보
                    Integer.parseInt(df.formatCellValue(row.getCell(colMap.get("AGE")))),// 나이
                    df.formatCellValue(row.getCell(colMap.get("CUST_TYP_CD"))),          // 고객유형코드
                    df.formatCellValue(row.getCell(colMap.get("CUST_DTL_TYP_CD"))),      // 고객세부유형코드
                    df.formatCellValue(row.getCell(colMap.get("ACNT_TYP_CD"))),          // 계정유형코드
                    df.formatCellValue(row.getCell(colMap.get("PAY_MTHD_CD"))),          // 납부방법코드
                    df.formatCellValue(row.getCell(colMap.get("SCRB_DT"))),              // 상품가입일자
                    df.formatCellValue(row.getCell(colMap.get("PROD_TERM_RSN_CD"))),     // 상품해지사유코드
                    df.formatCellValue(row.getCell(colMap.get("SVC_DTL_CL_CD"))),        // 서비스세부구분코드
                    df.formatCellValue(row.getCell(colMap.get("EQP_VER_NUM"))),          // eqp_ver_num
                    df.formatCellValue(row.getCell(colMap.get("EQP_MGMT_ST_CD"))),       // 단말기관리상태코드
                    df.formatCellValue(row.getCell(colMap.get("MKTG_DT"))),              // 단말기출시일자
                    df.formatCellValue(row.getCell(colMap.get("NW_MTHD_CD"))),           // network방식코드
                    df.formatCellValue(row.getCell(colMap.get("NATE_CL_CD"))),           // NATE구분코드
                    df.formatCellValue(row.getCell(colMap.get("AUTH_KEY_NUM"))),         // 인증키번호
                    df.formatCellValue(row.getCell(colMap.get("TRK_IDX_NUM"))),          // trkindex번호
                    df.formatCellValue(row.getCell(colMap.get("INT_PHON_STA_CL_CD"))),   // 국제전화발신금지여부
                    df.formatCellValue(row.getCell(colMap.get("PROD_ID"))),              // 서비스별상품id
                    df.formatCellValue(row.getCell(colMap.get("KIT_MDL_CD"))),           // 킷모델코드
                    df.formatCellValue(row.getCell(colMap.get("KIT_SER_NUM"))),          // 킷일련번호
                    df.formatCellValue(row.getCell(colMap.get("BEF_FEE_PROD_ID"))),      // 변경전요금상품id
                    prodList
            );
            custServiceMap.put(svcMgmtNum, info);
        }

        // 메모리 내용 출력
        log.info("===== custServiceMap({}) =====", custServiceMap.size());
        custServiceMap.forEach((key, value) -> {
            log.info("KEY[{}] : VALUE[{}]", key, value);
        });
    }

    /**
     * 단말 모델 정보 load
     * @param sheet
     * @return
     */
    private void loadEquipmentModel(Sheet sheet) {
        Map<String, Map<String, EquipmentModel.EquipmentModelLineup>> groupMap = new HashMap<>();
        Map<String, String[]> modelBasicInfo = new HashMap<>();

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

            groupMap.computeIfAbsent(mdlCd, k -> new HashMap<>()).put(itmCd, lnup);
        }

        groupMap.forEach((mdlCd, dtlMap) -> {
            String[] basic = modelBasicInfo.get(mdlCd);
            equipmentModelMap.put(mdlCd, new EquipmentModel(
                    mdlCd,
                    basic[0], // eqpMdlNm
                    basic[1], // verNum
                    Collections.unmodifiableMap(dtlMap)
            ));
        });

        // 메모리 내용 출력
        log.info("===== equipmentModelMap({}) =====", equipmentModelMap.size());
        equipmentModelMap.forEach((key, value) -> {
            log.info("KEY[{}] : VALUE({})[{}]", key, value.eqpMdlLnupMap().size(), value);
        });
    }

    /**
     * 구성항목값 정보 load
     * @param sheet
     * @return
     */
    private void loadConsItemValue(Sheet sheet) {
        DataFormatter df = new DataFormatter();

        Map<String, Integer> colMap = getColIndex(sheet);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String consItmId = df.formatCellValue(row.getCell(colMap.get("CONS_ITM_ID")));
            ConsItemValue info = new ConsItemValue(
                    consItmId,
                    df.formatCellValue(row.getCell(colMap.get("CONS_ITM_NM"))),
                    df.formatCellValue(row.getCell(colMap.get("CONS_ITM_VAL")))
            );

            consItemValueMap.put(consItmId, info);
        }

        // 메모리 내용 출력
        log.info("===== consItemValueMap({}) =====", consItemValueMap.size());
        consItemValueMap.forEach((key, value) -> {
            log.info("KEY[{}] : VALUE[{}]", key, value);
        });
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
        log.info("--- colMap:{}", colMap);
        return colMap;
    }

    /**
     * 고객 서비스 요약 정보 목록 및 건수 조회
     * @return
     */
    public ListResDto<CustServiceSummary> getCustServiceSummaryList() {
        List<CustServiceSummary> list = custServiceMap.values().stream()
                .map(c -> new CustServiceSummary(c.svcMgmtNum(), c.svcNum()))
                .toList();
        return new ListResDto<>(list.size(), list);
    }

    /**
     * 고객 서비스 정보 조회
     * @param svcMgmtNum
     * @return
     */
    public CustService getCustService(String svcMgmtNum) {
        return custServiceMap.get(svcMgmtNum);
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

    /**
     * 구성항목값 요약 정보 목록 및 건수 조회
     * @return
     */
    public ListResDto<ConsItemValueSummary> getConsItemValueSummaryList() {
        List<ConsItemValueSummary> list = consItemValueMap.values().stream()
                .map(e -> new ConsItemValueSummary(e.consItmId(), e.consItmNm(), e.consItmVal()))
                .toList();

        return new ListResDto<>(list.size(), list);
    }

    /**
     * 구성항목값 정보 조회
     * @param consItmId
     * @return
     */
    public ConsItemValue getConsItemValue(String consItmId) {
        return consItemValueMap.get(consItmId);
    }
}
