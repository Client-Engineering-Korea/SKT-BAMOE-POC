# 모듈 통계 분석: zordms0500210

## 1. 함수 구성

### 함수 개수
- **총 함수 개수**: 3개

### 각 함수별 입력 값

| 함수명 | 입력 파라미터 | 설명 |
|--------|--------------|------|
| [`zordms0500210`](../01_module_definition/others/pseudocode_zordms0500210.txt:6) | 입력.서비스관리번호, 입력.서비스코드 | 단말기 상태 확인 메인 함수 |
| [`a000_input_validation`](../01_module_definition/others/pseudocode_zordms0500210.txt:18) | 입력.서비스관리번호 | 입력 검증 및 초기값 설정 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) | 입력 (전체 객체) | 단말기 상태 확인 메인 처리 |

---

## 2. Rule 구성

### IF-THEN 개수 (NESTED 포함)

#### 함수별 IF-THEN 개수 테이블

| 함수명 | 기본 IF-THEN | NESTED IF-THEN | 총계 |
|--------|-------------|----------------|------|
| [`zordms0500210`](../01_module_definition/others/pseudocode_zordms0500210.txt:6) | 0 | 0 | 0 |
| [`a000_input_validation`](../01_module_definition/others/pseudocode_zordms0500210.txt:18) | 1 | 0 | 1 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) | 9 | 3 | 12 |
| **전체 합계** | **10** | **3** | **13** |

#### IF-THEN 상세 분석

**[`a000_input_validation`](../01_module_definition/others/pseudocode_zordms0500210.txt:18) 함수:**

1. **IF-THEN #1** (기본, line 20-23)
   ```
   IF 입력.서비스관리번호 = 0 THEN
       오류("서비스관리번호 필수")
       RETURN 실패
   ```

**[`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) 함수:**

1. **IF-THEN #2** (기본, line 35-42)
   ```
   IF 입력.서비스코드 = NULL THEN
       서비스정보 ← DB조회(...)
       IF 서비스정보 = NOT_FOUND THEN  [NESTED]
           출력.서비스단말상태코드 ← "3"
           RETURN 성공
   ```

2. **IF-THEN #3** (기본, line 44-48)
   ```
   IF 사용단말 = NOT_FOUND THEN
       출력.서비스단말상태코드 ← "3"
       RETURN 성공
   ```

3. **IF-THEN #4** (기본, line 56-62)
   ```
   IF 서비스최종이력.변경사유코드 ≠ "98" AND
      (서비스최종이력.단말관계변경코드 = "Z1" OR "Z2") THEN
       출력.IMEI상태여부 ← "Y"
       출력.서비스단말상태코드 ← "4"
       RETURN 성공
   ```

4. **IF-THEN #5** (기본, line 70-74)
   ```
   IF 단말최종서비스.서비스관리번호 = 입력.서비스관리번호 THEN
       출력.원소유단말모델코드 ← ...
   ```

5. **IF-THEN #6** (기본, line 77-81)
   ```
   IF DS단말이력.단말관계변경코드 = "F3" AND 변경사유코드 = "47" THEN
       출력.원소유단말모델코드 ← ""
   ```

6. **IF-THEN #7** (기본, line 85-88)
   ```
   IF 출력.사용단말모델코드 = NULL THEN
       출력.서비스단말상태코드 ← "3"
       RETURN 성공
   ```

7. **IF-THEN #8** (기본, line 90-97)
   ```
   IF 출력.사용단말모델코드 = 출력.원소유단말모델코드 AND ... THEN
       IF USIM개통서비스.USIM가입서비스여부 = "Y" THEN  [NESTED]
           출력.서비스단말상태코드 ← "5"
       RETURN 성공
   ```

8. **IF-THEN #9** (기본, line 100-104)
   ```
   IF 임의기변이력.단말관계변경코드 = "C1" AND 변경사유코드 = "07" THEN
       출력.IMEI상태여부 ← "Y"
       출력.서비스단말상태코드 ← "2"
   ```

9. **IF-THEN #10** (기본, line 106-108)
   ```
   IF USIM개통서비스.USIM가입서비스여부 = "Y" THEN
       출력.서비스단말상태코드 ← "5"
   ```

#### IF-THEN 총계

| 구분 | 개수 |
|------|------|
| 기본 IF-THEN | 10 |
| NESTED IF-THEN | 3 |
| **총 IF-THEN** | **13** |

---

### DB조회 횟수

#### 함수별 DB조회 개수 테이블

| 함수명 | DB조회 횟수 |
|--------|------------|
| [`zordms0500210`](../01_module_definition/others/pseudocode_zordms0500210.txt:6) | 0 |
| [`a000_input_validation`](../01_module_definition/others/pseudocode_zordms0500210.txt:18) | 0 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) | 7 |
| **전체 합계** | **7** |

#### DB조회 상세 목록

**[`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) 함수:**

1. **DB조회 #1** (line 36)
   - 테이블: `zord_svc_s2018`
   - 조회 키: `입력.서비스관리번호`
   - 목적: 서비스 정보 조회

2. **DB조회 #2** (line 44)
   - 테이블: `zord_svc_eqp_rel_hst_s0018`
   - 조회 키: `입력.서비스관리번호`
   - 목적: 사용 단말 정보 조회

3. **DB조회 #3** (line 54)
   - 테이블: `zord_svc_eqp_rel_hst_s0026`
   - 조회 키: `입력.서비스관리번호`
   - 목적: 서비스 최종 이력 조회

4. **DB조회 #4** (line 64-68)
   - 테이블: `zord_svc_eqp_rel_hst_s0025`
   - 조회 키: `단말모델코드, 단말일련번호, 서비스코드, SIM일련번호`
   - 목적: 단말 최종 서비스 조회

5. **DB조회 #5** (line 76)
   - 테이블: `zord_svc_eqp_rel_hst_s0091`
   - 조회 키: (명시되지 않음)
   - 목적: DS 단말 이력 조회

6. **DB조회 #6** (line 83)
   - 테이블: `zord_svc_s4276`
   - 조회 키: `입력.서비스관리번호`
   - 목적: USIM 개통 서비스 조회

7. **DB조회 #7** (line 99)
   - 테이블: `zord_svc_eqp_rel_hst_s0027`
   - 조회 키: `입력.서비스관리번호`
   - 목적: 임의 기변 이력 조회

---

### IF-THEN + DB조회 Rule

#### 정의 설명
IF-THEN 조건문 내에서 DB조회 결과를 직접 사용하거나, DB조회 직후 그 결과를 조건으로 판단하는 Rule을 의미합니다.

#### 해당 Rule 목록

**[`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) 함수:**

1. **Rule #1** (line 36-42)
   - DB조회: `zord_svc_s2018` 조회
   - IF-THEN: `IF 서비스정보 = NOT_FOUND` (NESTED)
   - 설명: 서비스 정보 미존재 시 상태 3 설정

2. **Rule #2** (line 44-48)
   - DB조회: `zord_svc_eqp_rel_hst_s0018` 조회
   - IF-THEN: `IF 사용단말 = NOT_FOUND`
   - 설명: 사용 단말 미존재 시 상태 3 설정

3. **Rule #3** (line 54-62)
   - DB조회: `zord_svc_eqp_rel_hst_s0026` 조회
   - IF-THEN: `IF 변경사유코드 ≠ "98" AND 단말관계변경코드 = "Z1/Z2"`
   - 설명: IMEI 이상 상태 체크

4. **Rule #4** (line 64-74)
   - DB조회: `zord_svc_eqp_rel_hst_s0025` 조회
   - IF-THEN: `IF 단말최종서비스.서비스관리번호 = 입력.서비스관리번호`
   - 설명: 원소유 단말 정보 설정

5. **Rule #5** (line 76-81)
   - DB조회: `zord_svc_eqp_rel_hst_s0091` 조회
   - IF-THEN: `IF 단말관계변경코드 = "F3" AND 변경사유코드 = "47"`
   - 설명: DS 단말 이력 체크 및 원소유 정보 초기화

6. **Rule #6** (line 83, 90-97)
   - DB조회: `zord_svc_s4276` 조회
   - IF-THEN: 원소유 단말 일치 체크 및 USIM 가입 여부 (NESTED)
   - 설명: USIM 가입 서비스 상태 설정

7. **Rule #7** (line 99-104)
   - DB조회: `zord_svc_eqp_rel_hst_s0027` 조회
   - IF-THEN: `IF 단말관계변경코드 = "C1" AND 변경사유코드 = "07"`
   - 설명: 임의 기변 이력 체크

8. **Rule #8** (line 83, 106-108)
   - DB조회: `zord_svc_s4276` 조회 (재사용)
   - IF-THEN: `IF USIM가입서비스여부 = "Y"`
   - 설명: 최종 USIM 가입 상태 설정

#### IF-THEN + DB조회 Rule 총계

| 함수명 | Rule 개수 |
|--------|----------|
| [`zordms0500210`](../01_module_definition/others/pseudocode_zordms0500210.txt:6) | 0 |
| [`a000_input_validation`](../01_module_definition/others/pseudocode_zordms0500210.txt:18) | 0 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) | 8 |
| **전체 합계** | **8** |

---

## 3. 통계 요약

| 항목 | 개수 |
|------|------|
| 함수 개수 | 3 |
| IF-THEN 총 개수 | 13 |
| DB조회 총 개수 | 7 |
| IF-THEN + DB조회 Rule 개수 | 8 |

---

## 4. 함수별 복잡도 분석

### 복잡도 점수 계산식
```
복잡도 점수 = IF-THEN 개수 + DB조회 개수 + (IF-THEN + DB조회 Rule 개수)
```

### 함수별 복잡도 점수 테이블

| 함수명 | IF-THEN | DB조회 | IF+DB Rule | 복잡도 점수 | 복잡도 등급 |
|--------|---------|--------|------------|------------|------------|
| [`zordms0500210`](../01_module_definition/others/pseudocode_zordms0500210.txt:6) | 0 | 0 | 0 | 0 | 낮음 |
| [`a000_input_validation`](../01_module_definition/others/pseudocode_zordms0500210.txt:18) | 1 | 0 | 0 | 1 | 낮음 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) | 12 | 7 | 8 | 27 | 매우 높음 |

**복잡도 등급 기준:**
- 낮음: 1-3점
- 중간: 4-7점
- 높음: 8-12점
- 매우 높음: 13점 이상

---

## 5. 조회 테이블 통계

### 테이블별 조회 정보

| 테이블명 | 조회 횟수 | 사용 함수 |
|---------|----------|----------|
| `zord_svc_s2018` | 1 | [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) |
| `zord_svc_eqp_rel_hst_s0018` | 1 | [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) |
| `zord_svc_eqp_rel_hst_s0026` | 1 | [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) |
| `zord_svc_eqp_rel_hst_s0025` | 1 | [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) |
| `zord_svc_eqp_rel_hst_s0091` | 1 | [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) |
| `zord_svc_s4276` | 1 | [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) |
| `zord_svc_eqp_rel_hst_s0027` | 1 | [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0500210.txt:33) |

### 총 테이블 수
- **조회 대상 테이블 수**: 7개

---

## 6. Rule 패턴 분석

### Rule 유형별 분류

| Rule 유형 | 개수 | 비율 |
|-----------|------|------|
| 입력 검증 | 1 | 7.7% |
| DB조회 결과 검증 (NESTED 포함) | 3 | 23.1% |
| 단말 상태 체크 | 4 | 30.8% |
| 원소유 단말 처리 | 2 | 15.4% |
| USIM 가입 체크 | 2 | 15.4% |
| 기타 | 1 | 7.7% |
| **전체** | **13** | **100%** |

### 복잡도 특성

1. **매우 높은 복잡도**
   - 13개의 IF-THEN (3개 NESTED 포함)
   - 7개의 DB조회
   - 8개의 IF-THEN + DB조회 Rule
   - 복잡도 점수: 27점 (매우 높음)

2. **다단계 단말 상태 체크**
   - 서비스 정보 → 사용 단말 → 이력 추적
   - 각 단계별 상태 코드 결정
   - 우선순위에 따른 상태 판단

3. **NESTED 구조**
   - 서비스 정보 조회 내부에 NOT_FOUND 체크
   - 원소유 단말 일치 내부에 USIM 가입 체크
   - 복잡한 조건 검증

### DB 조회 패턴

1. **다중 순차 조회**
   - 7개 테이블 순차 조회
   - 각 조회 결과를 다음 조회에 활용
   - 복잡한 데이터 추적

2. **이력 테이블 중심**
   - 대부분 이력 테이블 조회
   - 단말 변경 이력 추적
   - 시점별 상태 파악

---

## 7. 확장성 분석

### 실제 모듈 규모 추정

현재 분석된 모듈은 **단말기 상태 확인 모듈**로, 서비스의 단말기 상태를 종합적으로 확인합니다.

**예상 호출 빈도:**
- 단말 변경 시: 매번 호출
- 서비스 조회 시: 필요 시 호출
- 예상 일일 호출: 10,000+ 회

### Rule 함수 증가 패턴

**확장 가능 영역:**

1. **추가 단말 상태 코드**
   ```
   IF 단말상태 = "분실" THEN
       출력.서비스단말상태코드 ← "6"
   ELSE IF 단말상태 = "도난" THEN
       출력.서비스단말상태코드 ← "7"
   ```
   - 예상 증가: +2 IF-THEN

2. **IMEI 블랙리스트 체크**
   ```
   IMEI블랙리스트 ← DB조회("imei_blacklist", 사용단말.IMEI)
   IF IMEI블랙리스트 ≠ NOT_FOUND THEN
       출력.IMEI상태여부 ← "Y"
       출력.서비스단말상태코드 ← "8"
   ```
   - 예상 증가: +1 IF-THEN, +1 DB조회

3. **단말 이력 상세 추적**
   ```
   단말이력목록 ← DB조회_전체("zord_svc_eqp_rel_hst", 서비스관리번호)
   FOR EACH 이력 IN 단말이력목록
       IF 이력.단말관계변경코드 IN ("Z1", "Z2", "C1") THEN
           이력상세.추가(이력)
   ```
   - 예상 증가: +1 IF-THEN, +1 DB조회

### 예상 통계 (확장 후)

| 항목 | 현재 | 확장 후 예상 | 증가율 |
|------|------|-------------|--------|
| 함수 개수 | 3 | 4-5 | +33-67% |
| IF-THEN 개수 | 13 | 16-20 | +23-54% |
| DB조회 개수 | 7 | 9-11 | +29-57% |
| 복잡도 (b000_main_proc) | 27 | 35-42 | +30-56% |

---

## 9. 버전 정보

| 항목 | 내용 |
|------|------|
| **분석 일자** | 2026-03-31 |
| **분석자** | Bob (AI Assistant) |
| **문서 버전** | 1.0 |
| **기반 Pseudocode** | [`zordms0500210.txt`](../01_module_definition/others/pseudocode_zordms0500210.txt) (114 lines) |