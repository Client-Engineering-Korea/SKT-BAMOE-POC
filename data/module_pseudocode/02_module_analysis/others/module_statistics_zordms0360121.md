# 모듈 통계 분석: zordms0360121

## 1. 함수 구성

### 함수 개수
- **총 함수 개수**: 8개

### 각 함수별 입력 값

| 함수명 | 입력 파라미터 | 설명 |
|--------|--------------|------|
| [`zordms0360121`](../01_module_definition/others/pseudocode_zordms0360121.txt:6) | 입력 (전체 객체) | 무선 기본요금제 기준정보 체크 메인 함수 |
| [`a000_input_validation`](../01_module_definition/others/pseudocode_zordms0360121.txt:20) | 입력 (전체 객체) | 입력 검증 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360121.txt:30) | 입력 (전체 객체) | 메인 처리 로직 |
| [`c101_chk1_no_lmt_cnt_prcpln_chg`](../01_module_definition/others/pseudocode_zordms0360121.txt:39) | 입력 (전체 객체) | 특정 요금제 제한 완화 체크 |
| [`c102_chk2_no_lmt_cnt_prcpln_chg`](../01_module_definition/others/pseudocode_zordms0360121.txt:50) | 입력 (전체 객체) | 세대간 변경 제한 완화 체크 |
| [`p010_set_initial_val`](../01_module_definition/others/pseudocode_zordms0360121.txt:61) | 입력 (전체 객체) | 초기값 설정 |
| [`p100_chk_mndt_item`](../01_module_definition/others/pseudocode_zordms0360121.txt:70) | 입력 (전체 객체) | 필수 항목 체크 |
| [`p200_chk_mtcg_item`](../01_module_definition/others/pseudocode_zordms0360121.txt:81) | 입력 (전체 객체) | 선택 항목 체크 |

---

## 2. Rule 구성

### IF-THEN 개수 (NESTED 포함)

#### 함수별 IF-THEN 개수 테이블

| 함수명 | 기본 IF-THEN | NESTED IF-THEN | 총계 |
|--------|-------------|----------------|------|
| [`zordms0360121`](../01_module_definition/others/pseudocode_zordms0360121.txt:6) | 0 | 0 | 0 |
| [`a000_input_validation`](../01_module_definition/others/pseudocode_zordms0360121.txt:20) | 0 | 0 | 0 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360121.txt:30) | 0 | 0 | 0 |
| [`c101_chk1_no_lmt_cnt_prcpln_chg`](../01_module_definition/others/pseudocode_zordms0360121.txt:39) | 1 | 0 | 1 |
| [`c102_chk2_no_lmt_cnt_prcpln_chg`](../01_module_definition/others/pseudocode_zordms0360121.txt:50) | 1 | 0 | 1 |
| [`p010_set_initial_val`](../01_module_definition/others/pseudocode_zordms0360121.txt:61) | 0 | 0 | 0 |
| [`p100_chk_mndt_item`](../01_module_definition/others/pseudocode_zordms0360121.txt:70) | 1 | 0 | 1 |
| [`p200_chk_mtcg_item`](../01_module_definition/others/pseudocode_zordms0360121.txt:81) | 1 | 0 | 1 |
| **전체 합계** | **4** | **0** | **4** |

#### IF-THEN 상세 분석

**[`c101_chk1_no_lmt_cnt_prcpln_chg`](../01_module_definition/others/pseudocode_zordms0360121.txt:39) 함수:**

1. **IF-THEN #1** (기본, line 41-44)
   ```
   IF 변경전요금제ID = "NA00006549" OR "NA00006550" THEN
       출력.요금제변경횟수제한SKIP ← "Y"
       출력.가입해지제한일기준SKIP ← "Y"
   ```

**[`c102_chk2_no_lmt_cnt_prcpln_chg`](../01_module_definition/others/pseudocode_zordms0360121.txt:50) 함수:**

1. **IF-THEN #2** (기본, line 52-55)
   ```
   IF (변경코드 = "C1" OR "C2") AND 세대간변경여부 = "Y" THEN
       출력.요금제변경횟수제한SKIP ← "Y"
       출력.가입해지제한일기준SKIP ← "Y"
   ```

**[`p100_chk_mndt_item`](../01_module_definition/others/pseudocode_zordms0360121.txt:70) 함수:**

1. **IF-THEN #3** (기본, line 72-75)
   ```
   IF 서비스관리번호 = NULL THEN
       오류("서비스관리번호 필수")
       RETURN 실패
   ```

**[`p200_chk_mtcg_item`](../01_module_definition/others/pseudocode_zordms0360121.txt:81) 함수:**

1. **IF-THEN #4** (기본, line 83-86)
   ```
   IF 변경코드 NOT IN ("C1", "C2", "G1", "N1", "M1") THEN
       오류("유효하지 않은 변경코드")
       RETURN 실패
   ```

#### IF-THEN 총계

| 구분 | 개수 |
|------|------|
| 기본 IF-THEN | 4 |
| NESTED IF-THEN | 0 |
| **총 IF-THEN** | **4** |

---

### DB조회 횟수

#### 함수별 DB조회 개수 테이블

| 함수명 | DB조회 횟수 |
|--------|------------|
| 모든 함수 | 0 |
| **전체 합계** | **0** |

#### DB조회 상세 목록

이 모듈은 DB 조회를 수행하지 않습니다. 입력 파라미터만을 사용하여 조건을 체크합니다.

---

### IF-THEN + DB조회 Rule

#### 정의 설명
IF-THEN 조건문 내에서 DB조회 결과를 직접 사용하거나, DB조회 직후 그 결과를 조건으로 판단하는 Rule을 의미합니다.

#### 해당 Rule 목록

이 모듈은 DB 조회를 수행하지 않으므로 해당 Rule이 없습니다.

#### IF-THEN + DB조회 Rule 총계

| 함수명 | Rule 개수 |
|--------|----------|
| 모든 함수 | 0 |
| **전체 합계** | **0** |

---

## 3. 통계 요약

| 항목 | 개수 |
|------|------|
| 함수 개수 | 8 |
| IF-THEN 총 개수 | 4 |
| DB조회 총 개수 | 0 |
| IF-THEN + DB조회 Rule 개수 | 0 |

---

## 4. 함수별 복잡도 분석

### 복잡도 점수 계산식
```
복잡도 점수 = IF-THEN 개수 + DB조회 개수 + (IF-THEN + DB조회 Rule 개수)
```

### 함수별 복잡도 점수 테이블

| 함수명 | IF-THEN | DB조회 | IF+DB Rule | 복잡도 점수 | 복잡도 등급 |
|--------|---------|--------|------------|------------|------------|
| [`zordms0360121`](../01_module_definition/others/pseudocode_zordms0360121.txt:6) | 0 | 0 | 0 | 0 | 낮음 |
| [`a000_input_validation`](../01_module_definition/others/pseudocode_zordms0360121.txt:20) | 0 | 0 | 0 | 0 | 낮음 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360121.txt:30) | 0 | 0 | 0 | 0 | 낮음 |
| [`c101_chk1_no_lmt_cnt_prcpln_chg`](../01_module_definition/others/pseudocode_zordms0360121.txt:39) | 1 | 0 | 0 | 1 | 낮음 |
| [`c102_chk2_no_lmt_cnt_prcpln_chg`](../01_module_definition/others/pseudocode_zordms0360121.txt:50) | 1 | 0 | 0 | 1 | 낮음 |
| [`p010_set_initial_val`](../01_module_definition/others/pseudocode_zordms0360121.txt:61) | 0 | 0 | 0 | 0 | 낮음 |
| [`p100_chk_mndt_item`](../01_module_definition/others/pseudocode_zordms0360121.txt:70) | 1 | 0 | 0 | 1 | 낮음 |
| [`p200_chk_mtcg_item`](../01_module_definition/others/pseudocode_zordms0360121.txt:81) | 1 | 0 | 0 | 1 | 낮음 |

**복잡도 등급 기준:**
- 낮음: 1-3점
- 중간: 4-7점
- 높음: 8-12점
- 매우 높음: 13점 이상

---

## 5. 조회 테이블 통계

### 테이블별 조회 정보

이 모듈은 DB 조회를 수행하지 않습니다.

### 총 테이블 수
- **조회 대상 테이블 수**: 0개

---

## 6. Rule 패턴 분석

### Rule 유형별 분류

| Rule 유형 | 개수 | 비율 |
|-----------|------|------|
| 특정 요금제 체크 | 1 | 25% |
| 세대간 변경 체크 | 1 | 25% |
| 필수 항목 검증 | 1 | 25% |
| 선택 항목 검증 | 1 | 25% |
| **전체** | **4** | **100%** |

### 복잡도 특성

1. **단순 구조**
   - 모든 IF-THEN이 단일 레벨
   - NESTED 구조 없음
   - 함수별 단일 책임

2. **함수 분리**
   - 8개 함수로 세분화
   - 각 함수는 명확한 단일 역할
   - 검증 로직 분리

3. **제한 완화 패턴**
   - SKIP 플래그 설정
   - 다른 모듈에서 플래그 확인
   - 유연한 제어 구조

### DB 조회 패턴

이 모듈은 DB 조회를 수행하지 않습니다.
- 입력 파라미터만으로 판단
- 빠른 처리 속도
- 외부 의존성 없음

---

## 7. 확장성 분석

### 실제 모듈 규모 추정

현재 분석된 모듈은 **요금제 변경 제한 완화 모듈**로, 특정 조건에서 제한을 SKIP 처리합니다.

**예상 호출 빈도:**
- 요금제 변경 시: 매번 호출
- 예상 일일 호출: 3,000+ 회

### Rule 함수 증가 패턴

**확장 가능 영역:**

1. **추가 요금제 등록**
   ```
   IF 변경전요금제ID IN ("NA00006549", "NA00006550", "NA00006551") THEN
       출력.요금제변경횟수제한SKIP ← "Y"
   ```
   - 예상 증가: 기존 IF-THEN 수정 (개수 변화 없음)

2. **추가 SKIP 조건**
   ```
   FUNCTION c103_chk3_vip_customer(입력)
       IF 고객.VIP등급 = "VVIP" THEN
           출력.요금제변경횟수제한SKIP ← "Y"
       END IF
   ```
   - 예상 증가: +1 함수, +1 IF-THEN

3. **부분 SKIP 지원**
   ```
   FUNCTION c104_chk4_partial_skip(입력)
       IF 조건 충족 THEN
           출력.요금제변경횟수제한SKIP ← "Y"
           출력.가입해지제한일기준SKIP ← "N"
       END IF
   ```
   - 예상 증가: +1 함수, +1 IF-THEN

### 예상 통계 (확장 후)

| 항목 | 현재 | 확장 후 예상 | 증가율 |
|------|------|-------------|--------|
| 함수 개수 | 8 | 10-12 | +25-50% |
| IF-THEN 개수 | 4 | 6-8 | +50-100% |
| DB조회 개수 | 0 | 0-1 | 0-100% |
| 복잡도 (평균) | 0.5 | 0.7 | +40% |

---

## 9. 버전 정보

| 항목 | 내용 |
|------|------|
| **분석 일자** | 2026-03-31 |
| **분석자** | Bob (AI Assistant) |
| **문서 버전** | 1.0 |
| **기반 Pseudocode** | [`zordms0360121.txt`](../01_module_definition/others/pseudocode_zordms0360121.txt) (91 lines) |