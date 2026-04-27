package org.acme.fact.domain;

import java.util.List;

//  고객가입 정보 Record
public record CustSubscribe(
        String svcMgmtNum,          // 서비스관리번호
        String svcNum,              // 서비스번호(전화회선번호)
        String feeProdId,           // 요금제ID
        String svcTypCd,            // 이용종류코드
        String svcDtlClCd,          // 상세구분코드
        String birthDt,             // 생년월일정보
        String custTypCd,           // 고객유형코드
        String custDtlTypCd,        // 세부유형코드
        String addProdCnt,          // 부가상품건수
        String eqpMdlCd,            // 단말모델정보
        String svcScrbDt,           // 서비스가입일자
        String svcTermDt,           // 서비스해지일자
        List<CustProd> prodLst      // 상품목록
) {
    // 고객상품 정보 Record
    public record CustProd(
            String prodTypCd,       // 상품유형코드
            String prodId,          // 상품ID
            String prodNm           // 상품명
    ) {}
}
