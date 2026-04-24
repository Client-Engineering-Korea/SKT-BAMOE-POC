# 모듈 통계 분석: zordmcom01040

## 1. 함수 구성

### 함수 개수
- **총 함수 개수**: 2개

### 각 함수별 입력 값

| 함수명 | 입력 파라미터 | 설명 |
|--------|--------------|------|
| [`zordmcom01040`](../01_module_definition/others/pseudocode_zordmcom01040.txt:6) | 입력.코드그룹, 입력.코드값 | 공통 코드 조회 및 검증 |
| [`코드값변환`](../01_module_definition/others/pseudocode_zordmcom01040.txt:43) | 코드그룹, 코드값 | 코드값을 코드명으로 변환 |

---

## 2. Rule 구성

### IF-THEN 개수 (NESTED 포함)

#### 함수별 IF-THEN 개수 테이블

| 함수명 | 기본 IF-THEN | NESTED IF-THEN | 총계 |
|--------|-------------|----------------|------|
| [`zordmcom01040`](../01_module_definition/others/pseudocode_zordmcom01040.txt:6) | 3 | 0 | 3 |
| [`코드값변환`](../01_module_definition/others/pseudocode_zordmcom01040.txt:43) | 1 | 0 | 1 |
| **전체 합계** | **4** | **0** | **4** |

#### IF-THEN 상세 분석

**[`zordmcom01040`](../01_module_definition/others/pseudocode_zordmcom01040.txt:6) 함수:**

1. **IF-THEN #1** (기본, line 10-15)
   ```
   IF 공통코드 = NOT_FOUND THEN
       출력.코드존재여부 ← "N"
       출력.유효여부 ← "N"
       RETURN 성공
   ```

2. **IF-THEN #2** (기본, line 21-25)
   ```
   IF 현재일자 < 공통코드.시작일 OR 현재일자 > 공통코드.종료일 THEN
       출력.유효여부 ← "N"
       출력.메시지 ← "유효기간 아님"
       RETURN 성공
   ```

3. **IF-THEN #3** (기본, line 27-31)
   ```
   IF 공통코드.사용여부 = "N" THEN
       출력.유효여부 ← "N"
       출력.메시지 ← "사용 중지된 코드"
       RETURN 성공
   ```

**[`코드값변환`](../01_module_definition/others/pseudocode_zordmcom01040.txt:43) 함수:**

1. **IF-THEN #1** (기본, line 47-51)
   ```
   IF 코드정보.유효여부 = "Y" THEN
       RETURN 코드정보.코드명
   ELSE
       RETURN "알 수 없음"
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
| [`zordmcom01040`](../01_module_definition/others/pseudocode_zordmcom01040.txt:6) | 1 |
| [`코드값변환`](../01_module_definition/others/pseudocode_zordmcom01040.txt:43) | 0 (간접 호출) |
| **전체 합계** | **1** |

#### DB조회 상세 목록

**[`zordmcom01040`](../01_module_definition/others/pseudocode_zordmcom01040.txt:6) 함수:**

1. **DB조회 #1** (line 8)
   - 테이블: `공통코드테이블`
   - 조회 키: `입력.코드그룹, 입력.코드값`
   - 목적: 공통 코드 정보 조회

---

### IF-THEN + DB조회 Rule

#### 정의 설명
IF-THEN 조건문 내에서 DB조회 결과를 직접 사용하거나, DB조회 직후 그 결과를 조건으로 판단하는 Rule을 의미합니다.

#### 해당 Rule 목록

**[`zordmcom01040`](../01_module_definition/others/pseudocode_zordmcom01040.txt:6) 함수:**

1. **Rule #1** (line 8-15)
   - DB조회: `공통코드테이블` 조회
   - IF-THEN: `IF 공통코드 = NOT_FOUND`
   - 설명: DB조회 결과가 없을 경우 코드 미존재 처리

#### IF-THEN + DB조회 Rule 총계

| 함수명 | Rule 개수 |
|--------|----------|
| [`zordmcom01040`](../01_module_definition/others/pseudocode_zordmcom01040.txt:6) | 1 |
| [`코드값변환`](../01_module_definition/others/pseudocode_zordmcom01040.txt:43) | 0 |
| **전체 합계** | **1** |

---

## 3. 통계 요약

| 항목 | 개수 |
|------|------|
| 함수 개수 | 2 |
| IF-THEN 총 개수 | 4 |
| DB조회 총 개수 | 1 |
| IF-THEN + DB조회 Rule 개수 | 1 |

---

## 4. 함수별 복잡도 분석

### 복잡도 점수 계산식
```
복잡도 점수 = IF-THEN 개수 + DB조회 개수 + (IF-THEN + DB조회 Rule 개수)
```

### 함수별 복잡도 점수 테이블

| 함수명 | IF-THEN | DB조회 | IF+DB Rule | 복잡도 점수 | 복잡도 등급 |
|--------|---------|--------|------------|------------|------------|
| [`zordmcom01040`](../01_module_definition/others/pseudocode_zordmcom01040.txt:6) | 3 | 1 | 1 | 5 | 중간 |
| [`코드값변환`](../01_module_definition/others/pseudocode_zordmcom01040.txt:43) | 1 | 0 | 0 | 1 | 낮음 |

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
| `공통코드테이블` | 1 | [`zordmcom01040`](../01_module_definition/others/pseudocode_zordmcom01040.txt:6) |

### 총 테이블 수
- **조회 대상 테이블 수**: 1개

---

## 6. Rule 패턴 분석

### Rule 유형별 분류

| Rule 유형 | 개수 | 비율 |
|-----------|------|------|
| 단순 조건 분기 | 3 | 75% |
| DB조회 결과 검증 | 1 | 25% |
| **전체** | **4** | **100%** |

### 복잡도 특성

1. **단순 구조**
   - 대부분 단일 레벨 IF-THEN
   - NESTED 구조 없음
   - 순차적 검증 패턴

2. **검증 중심**
   - 코드 존재 여부 체크
   - 유효기간 검증
   - 사용 여부 확인

### DB 조회 패턴

1. **단일 조회 패턴**
   - 1회의 DB조회로 모든 정보 획득
   - 조회 결과 재사용
   - 효율적인 데이터 접근

2. **캐싱 가능성**
   - 공통코드는 변경 빈도 낮음
   - 메모리 캐싱 적용 권장

---

## 7. 확장성 분석

### 실제 모듈 규모 추정

현재 분석된 모듈은 **기본 유틸리티 모듈**로, 다른 모듈에서 공통으로 사용됩니다.

**예상 호출 빈도:**
- 화면 표시: 코드명 변환 시 매번 호출
- 데이터 검증: 입력값 검증 시 호출
- 예상 일일 호출: 10,000+ 회

### Rule 함수 증가 패턴

**확장 가능 영역:**

1. **추가 검증 Rule**
   ```
   IF 공통코드.권한레벨 > 사용자.권한레벨 THEN
       출력.유효여부 ← "N"
       출력.메시지 ← "권한 부족"
   ```
   - 예상 증가: +1 IF-THEN

2. **다국어 지원**
   ```
   IF 입력.언어코드 = "EN" THEN
       출력.코드명 ← 공통코드.코드명_EN
   ELSE IF 입력.언어코드 = "JP" THEN
       출력.코드명 ← 공통코드.코드명_JP
   ```
   - 예상 증가: +2 IF-THEN

3. **캐시 조회 추가**
   ```
   캐시데이터 ← 캐시조회(코드그룹, 코드값)
   IF 캐시데이터 EXISTS THEN
       RETURN 캐시데이터
   ```
   - 예상 증가: +1 IF-THEN, +1 캐시조회

### 예상 통계 (확장 후)

| 항목 | 현재 | 확장 후 예상 | 증가율 |
|------|------|-------------|--------|
| 함수 개수 | 2 | 3-4 | +50-100% |
| IF-THEN 개수 | 4 | 8-10 | +100-150% |
| DB조회 개수 | 1 | 1 | 0% (캐시 활용) |
| 복잡도 (평균) | 3.0 | 4.5 | +50% |

---

## 9. 버전 정보

| 항목 | 내용 |
|------|------|
| **분석 일자** | 2026-03-31 |
| **분석자** | Bob (AI Assistant) |
| **문서 버전** | 1.0 |
| **기반 Pseudocode** | [`zordmcom01040.txt`](../01_module_definition/others/pseudocode_zordmcom01040.txt) (55 lines) |