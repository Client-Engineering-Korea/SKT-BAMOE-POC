package org.acme.fact.domain;

import java.util.List;

//  고객서비스 정보 Record
public record CustService (
    String cntrctMgmtNum,       // 계약관리번호
    String useCntrctClCd,       // 이용계약구분코드
    String useCntrctStCd,       // 이용계약상태코드
    String cntrctDt,            // 계약일자
    String coClCd,              // 회사구분코드
    String taxBillIsueYn,       // 세금계산서발행여부
    String nmCustNum,           // 명의고객번호
    String acntNum,             // 계정번호
    String svcMgmtNum,          // 서비스관리번호
    String svcCd,               // 서비스구분코드
    String svcNum,              // 서비스번호
    String svcStCd,             // 서비스상태코드
    String svcStChgCd,          // 서비스상태변경코드
    String svcChgRsnCd,         // 서비스변경사유코드
    String svcTypCd,            // 서비스이용종류코드
    String svcTypNm,            // 서비스이용종류명
    String svcScrbDt,           // 서비스가입일자
    String scrbReqRsnCd,        // 가입신청사유코드
    String svcTermDt,           // 서비스해지일자
    String feeProdId,           // 요금상품id
    String idntNumCd,           // 식별번호구분코드
    String grtmClCd,            // 보증금구분코드
    String wlfDcCd,             // 복지할인유형코드
    String wlfDcNm,             // 복지할인유형명
    String webMbrScrbClCd,      // 웹회원가입구분코드(TWORLD가입여부)
    String webMbrReqAgreeYn,    // 웹회원신청동의여부(TWORLD동의여부)
    String eqpMdlCd,            // 단말기모델코드
    String eqpSerNum,           // 단말기일련번호
    String simSerNum,           // sim일련번호
    String eqpUsgCd,            // 단말기용도코드
    String eqpMthdCd,           // 단말기방식코드
    String relSvcMgmtNum,       // 관계서비스관리번호
    String relFeeProdId,        // 관계서비스요금제
    String ctzCorpBizSerNum,    // 주민법인사업자등록일련번호
    String ctzCorpBizNumPinf,   // 주민법인사업자등록번호부분정보
    Integer age,                // 나이
    String custTypCd,           // 고객유형코드
    String custDtlTypCd,        // 고객세부유형코드
    String acntTypCd,           // 계정유형코드
    String payMthdCd,           // 납부방법코드
    String scrbDt,              // 상품가입일자
    String prodTermRsnCd,       // 상품해지사유코드
    String svcDtlClCd,          // 서비스세부구분코드
    String eqpVerNum,           // eqp_ver_num
    String eqpMgmtStCd,         // 단말기관리상태코드
    String mktgDt,              // 단말기출시일자
    String nwMthdCd,            // network방식코드
    String nateClCd,            // NATE구분코드
    String authKeyNum,          // 인증키번호
    String trkIdxNum,           // trkindex번호
    String intPhonStaClCd,      // 국제전화발신금지여부
    String prodId,              // 서비스별상품id
    String kitMdlCd,            // 킷모델코드
    String kitSerNum,           // 킷일련번호
    List<CustProd> prodLst      // 상품목록
) {
    // 고객상품 정보 Record
    public record CustProd(
            String prodTypCd,       // 상품유형코드
            String prodId,          // 상품ID
            String prodNm           // 상품명
    ) {}
}
