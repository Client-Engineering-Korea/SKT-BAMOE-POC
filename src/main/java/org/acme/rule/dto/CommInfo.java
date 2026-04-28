package org.acme.rule.dto;

import java.io.Serializable;

/**
 * 공통 변경 요청 정보 (ZORDMS0350010 의 COMM_INFO 영역).
 *
 * <p>chgCd: 변경코드 (G1=요금제변경, I1/I2=부가/부가요금제, D4/D5=해지)
 * <p>chgRsnCd: 변경사유코드
 * <p>canYn: 취소여부 (Y/N)
 * <p>chnlClCd: 채널구분코드 (NGM, NAP 등)
 * <p>onlineBatchCode: 온라인/배치 구분 ("0"=온라인, "1"=배치)
 * <p>opCtt1~10: 추가 처리 정보 (확장 필드)
 */
public record CommInfo(
        String chgCd,
        String chgRsnCd,
        String canYn,
        String chnlClCd,
        String onlineBatchCode,
        String opCtt1,
        String opCtt2,
        String opCtt3,
        String opCtt4,
        String opCtt5,
        String opCtt6,
        String opCtt7,
        String opCtt8,
        String opCtt9,
        String opCtt10
) implements Serializable {}
