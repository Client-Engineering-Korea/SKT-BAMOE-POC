# 모듈 통계 분석: zordms0360050

## 1. 함수 구성

### 함수 개수
- **총 함수 개수**: 4개

### 각 함수별 입력 값

| 함수명 | 입력 파라미터 | 설명 |
|--------|--------------|------|
| [`zordms0360050`](../01_module_definition/others/pseudocode_zordms0360050.txt:6) | 입력 (전체 객체) | 상품 정보 조회 및 target_prod_list 설정 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360050.txt:17) | 입력 (전체 객체) | 상품 정보 DB 조회 및 검증 |
| [`z900_target_prod_list_set`](../01_module_definition/others/pseudocode_zordms0360050.txt:39) | 입력, 상품정보 | target_prod_list 배열에 상품 정보 설정 |
| [`m000_msg_append`](../01_module_definition/others/pseudocode_zordms0360050.txt:58) | 메시지ID, 메시지타입 | 오류 메시지 추가 |

---

## 2. Rule 구성

### IF-THEN 개수 (NESTED 포함)

#### 함수별 IF-THEN 개수 테이블

| 함수명 | 기본 IF-THEN | NESTED IF-THEN | 총계 |
|--------|-------------|----------------|------|
| [`zordms0360050`](../01_module_definition/others/pseudocode_zordms0360050.txt:6) | 0 | 0 | 0 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360050.txt:17) | 2 | 0 | 2 |
| [`z900_target_prod_list_set`](../01_module_definition/others/pseudocode_zordms0360050.txt:39) | 0 | 0 | 0 |
| [`m000_msg_append`](../01_module_definition/others/pseudocode_zordms0360050.txt:58) | 0 | 0 | 0 |
| **전체 합계** | **2** | **0** | **2** |

#### IF-THEN 상세 분석

**[`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360050.txt:17) 함수:**

1. **IF-THEN #1** (기본, line 21-25)
   ```
   IF 상품정보 = NOT_FOUND THEN
       오류("상품정보 없음")
       CALL m000_msg_append("ZORDEA099", "E")
       RETURN 실패
   ```

2. **IF-THEN #2** (기본, line 27-31)
   ```
   IF DB오류 THEN
       오류("DB 조회 오류")
       CALL m000_msg_append("ZORDEA098", "E")
       RETURN 실패
   ```

#### IF-THEN 총계

| 구분 | 개수 |
|------|------|
| 기본 IF-THEN | 2 |
| NESTED IF-THEN | 0 |
| **총 IF-THEN** | **2** |

---

### DB조회 횟수

#### 함수별 DB조회 개수 테이블

| 함수명 | DB조회 횟수 |
|--------|------------|
| [`zordms0360050`](../01_module_definition/others/pseudocode_zordms0360050.txt:6) | 0 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360050.txt:17) | 1 |
| [`z900_target_prod_list_set`](../01_module_definition/others/pseudocode_zordms0360050.txt:39) | 0 |
| [`m000_msg_append`](../01_module_definition/others/pseudocode_zordms0360050.txt:58) | 0 |
| **전체 합계** | **1** |

#### DB조회 상세 목록

**[`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360050.txt:17) 함수:**

1. **DB조회 #1** (line 19)
   - 테이블: `pdb_zord_pdr_obj_bas_info_s0005`
   - 조회 키: `입력.상품ID, 입력.처리일시`
   - 목적: 상품 기본 정보 조회

---

### IF-THEN + DB조회 Rule

#### 정의 설명
IF-THEN 조건문 내에서 DB조회 결과를 직접 사용하거나, DB조회 직후 그 결과를 조건으로 판단하는 Rule을 의미합니다.

#### 해당 Rule 목록

**[`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360050.txt:17) 함수:**

1. **Rule #1** (line 19-25)
   - DB조회: `pdb_zord_pdr_obj_bas_info_s0005` 조회
   - IF-THEN: `IF 상품정보 = NOT_FOUND`
   - 설명: DB조회 결과가 없을 경우 오류 처리

2. **Rule #2** (line 19, 27-31)
   - DB조회: `pdb_zord_pdr_obj_bas_info_s0005` 조회
   - IF-THEN: `IF DB오류`
   - 설명: DB조회 중 오류 발생 시 처리

#### IF-THEN + DB조회 Rule 총계

| 함수명 | Rule 개수 |
|--------|----------|
| [`zordms0360050`](../01_module_definition/others/pseudocode_zordms0360050.txt:6) | 0 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360050.txt:17) | 2 |
| [`z900_target_prod_list_set`](../01_module_definition/others/pseudocode_zordms0360050.txt:39) | 0 |
| [`m000_msg_append`](../01_module_definition/others/pseudocode_zordms0360050.txt:58) | 0 |
| **전체 합계** | **2** |

---

## 3. 통계 요약

| 항목 | 개수 |
|------|------|
| 함수 개수 | 4 |
| IF-THEN 총 개수 | 2 |
| DB조회 총 개수 | 1 |
| IF-THEN + DB조회 Rule 개수 | 2 |

---

## 4. 함수별 복잡도 분석

### 복잡도 점수 계산식
```
복잡도 점수 = IF-THEN 개수 + DB조회 개수 + (IF-THEN + DB조회 Rule 개수)
```

### 함수별 복잡도 점수 테이블

| 함수명 | IF-THEN | DB조회 | IF+DB Rule | 복잡도 점수 | 복잡도 등급 |
|--------|---------|--------|------------|------------|------------|
| [`zordms0360050`](../01_module_definition/others/pseudocode_zordms0360050.txt:6) | 0 | 0 | 0 | 0 | 낮음 |
| [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360050.txt:17) | 2 | 1 | 2 | 5 | 중간 |
| [`z900_target_prod_list_set`](../01_module_definition/others/pseudocode_zordms0360050.txt:39) | 0 | 0 | 0 | 0 | 낮음 |
| [`m000_msg_append`](../01_module_definition/others/pseudocode_zordms0360050.txt:58) | 0 | 0 | 0 | 0 | 낮음 |

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
| `pdb_zord_pdr_obj_bas_info_s0005` | 1 | [`b000_main_proc`](../01_module_definition/others/pseudocode_zordms0360050.txt:17) |

### 총 테이블 수
- **조회 대상 테이블 수**: 1개

---

## 6. Rule 패턴 분석

### Rule 유형별 분류

| Rule 유형 | 개수 | 비율 |
|-----------|------|------|
| DB조회 결과 검증 | 1 | 50% |
| DB오류 처리 | 1 | 50% |
| **전체** | **2** | **100%** |

### 복잡도 특성

1. **단순 구조**
   - 모든 IF-THEN이 단일 레벨
   - NESTED 구조 없음
   - 명확한 오류 처리

2. **함수 분리**
   - 진입점 함수: 데이터 정제
   - 메인 처리 함수: DB조회 및 검증
   - 설정 함수: 출력 구조체 설정
   - 메시지 함수: 오류 메시지 처리

3. **오류 처리 패턴**
   - 표준화된 메시지 코드 사용
   - ZORDEA099: 상품정보 없음
   - ZORDEA098: DB 조회 오류

### DB 조회 패턴

1. **단일 조회 패턴**
   - 1회 DB조회로 필요한 정보 획득
   - 조회 결과 검증
   - 오류 시 즉시 반환

2. **이력 테이블 조회**
   - 처리일시 기준 조회
   - 시점별 상품 정보 관리

---

## 7. 확장성 분석

### 실제 모듈 규모 추정

현재 분석된 모듈은 **상품 정보 설정 모듈**로, target_prod_list 구조체에 상품 정보를 추가하는 역할을 합니다.

**예상 호출 빈도:**
- 상품 목록 조회 시: 상품 수만큼 호출
- 배치 처리 시: 대량 호출
- 예상 일일 호출: 50,000+ 회

### Rule 함수 증가 패턴

**확장 가능 영역:**

1. **추가 검증 로직**
   ```
   IF 상품정보.상품상태 = "중지" THEN
       오류("중지된 상품")
       RETURN 실패
   ```
   - 예상 증가: +1 IF-THEN

2. **배치 처리 지원**
   ```
   FOR EACH 상품 IN 입력.상품목록
       CALL b000_main_proc(상품)
   ```
   - 예상 증가: 함수 1개 추가

3. **캐싱 로직**
   ```
   캐시데이터 ← 캐시조회(상품ID)
   IF 캐시데이터 EXISTS THEN
       RETURN 캐시데이터
   ```
   - 예상 증가: +1 IF-THEN

### 예상 통계 (확장 후)

| 항목 | 현재 | 확장 후 예상 | 증가율 |
|------|------|-------------|--------|
| 함수 개수 | 4 | 6-7 | +50-75% |
| IF-THEN 개수 | 2 | 4-6 | +100-200% |
| DB조회 개수 | 1 | 1 | 0% (캐시 활용) |
| 복잡도 (평균) | 1.25 | 2.0 | +60% |

---

## 9. 버전 정보

| 항목 | 내용 |
|------|------|
| **분석 일자** | 2026-03-31 |
| **분석자** | Bob (AI Assistant) |
| **문서 버전** | 1.0 |
| **기반 Pseudocode** | [`zordms0360050.txt`](../01_module_definition/others/pseudocode_zordms0360050.txt) (65 lines) |