# SKT-BAMOE-POC — Claude Code 가이드

이 문서는 **SKT-BAMOE-POC (fact API 전용)** 레포에서 Claude 가 작업할 때 필요한 컨텍스트와 결정사항을 모은 가이드입니다.

---

## 1. 프로젝트 목적 (분리 후)

본 레포는 **고객(SKT) 측 데이터/외부모듈 API 시뮬레이터** 입니다. IBM BAMOE 룰 엔진(BPMN/DMN/DRL)이 HTTP 로 호출하는 **모든 데이터 소스를 흉내** 내는 역할:

1. **fact API**: Excel 인메모리 로딩으로 `CustService`, `EquipmentModel` 등 회선/단말 데이터 제공 (이미 구현)
2. **외부모듈 mock API** (향후): 고객이 직접 작성해 본 레포에 추가 예정. 예: `zordms0360100.f_ordrs03f0000754_check` 같이 고객 엑셀의 IF_TXT 가 비어있고 외부 메소드 호출만 있는 케이스 — 본 레포에 mock endpoint 로 노출.

**룰 엔진 (BPMN/DMN/DRL) 은 본 레포가 아닌 [SKT-BAMOE-POC-RULE](../SKT-BAMOE-POC-RULE) 에 있음.** (자세한 분리 경위는 §3)

---

## 2. 기술 스택

- **Framework**: Spring Boot 3.5.12
- **Language**: Java 17
- **Build**: Maven 3.9+
- **port**: 8081 (룰 엔진 인스턴스가 8080 으로 띄워서 본 레포의 8081 을 호출)
- **외부 의존성 없음**: BAMOE/Drools/Kogito/jBPM 모두 미사용 — 평범한 Spring Boot REST 서버

---

## 3. 레포 분리 (2026-04-28)

### 분리 전
`SKT-BAMOE-POC` 한 레포에 두 영역이 섞여있었음:
- `org.acme.fact.*` (고객 fact API)
- `org.acme.rule.*` + `org.acme.runtime.*` + `bpmn/` + `dmn/` (BAMOE 룰)

### 분리 후
| 레포 | 역할 |
|------|------|
| **SKT-BAMOE-POC** (본 레포) | 고객 측 데이터/외부모듈 API 시뮬레이터 |
| **SKT-BAMOE-POC-RULE** ([../SKT-BAMOE-POC-RULE](../SKT-BAMOE-POC-RULE)) | BAMOE 룰 엔진 (BPMN/DMN/DRL + wiring) |

### 룰 자산 보존 위치
본 레포에서 삭제한 룰 자산은 **`../tmp/skt-bamoe-poc-rule-snapshot/`** 에 보존되어 있음 (git 추적 밖). 룰 레포에서 작업 시 참조 가능. 자세한 내용은 해당 디렉토리의 `README.md` 참조.

### 사이드 레포 (RULE) 의 현재 상태
- 동료가 별도로 작업한 자산: `com.telco.*` 도메인 모델 + 4개 DRL Rule Unit (`D000000333Data` 등) + `.scesim` 테스트 + postman/Dockerfile
- BPMN/DMN 은 **본 레포 버전이 더 최신** (사이드 레포 것보다 새로움)
- **현재는 손대지 않음**: 고객이 본 레포 fact API 를 추가 작성해서 제공하면, 그때 룰 레포 통합 작업 진행 예정

---

## 4. 디렉토리 구조

```
SKT-BAMOE-POC/
├── pom.xml                                  Maven 빌드 (artifactId: skt-bamoe-poc-fact)
├── README.md                                실행 안내
├── CLAUDE.md                                ⭐ 이 문서
├── AGENTS.md                                코딩 가이드
├── src/
│   ├── main/
│   │   ├── java/org/acme/
│   │   │   ├── FactApiApplication.java      Spring Boot 진입점
│   │   │   ├── FactCorsConfig.java
│   │   │   └── fact/
│   │   │       ├── controller/FactDataController.java   GET /api/fact/cust/*, /api/fact/eqp/*
│   │   │       ├── service/FactDataService.java         Excel → HashMap 로딩 (@PostConstruct)
│   │   │       └── domain/                              CustService(45필드 + 가입 상품 List), EquipmentModel(라인업 Map), CustServiceSummary, EquipmentModelSummary, ListResDto
│   │   └── resources/
│   │       ├── application.properties        port=8081, fact.excelFilename
│   │       └── excel_data/fact_data.xlsx    회선 1건 + 단말 + 가입 상품 (3시트)
│   └── test/                                 (현재 비어있음 — 향후 fact API 단위 테스트)
├── data/                                     고객 원본 자료 (참고용, fact_data.xlsx 의 출처)
│   ├── excel_data/                          POC 수행 DATA, 상품유형간관계 DATA, SKT 상품Rule_Data 등 4개 .xlsx
│   └── module_pseudocode/                   의사코드 + 모듈 분석
└── tools/                                   Excel 시트 → JSON 덤프 스크립트
    ├── dump_excel.py
    └── excel_dump/                          5개 시트 덤프 결과
```

---

## 5. 핵심 도메인 모델

### CustService
**위치**: `src/main/java/org/acme/fact/domain/CustService.java`

**역할**: SKT 고객 한 명의 모든 정보를 담는 Java Record. `fact_data.xlsx` 의 `cust_svc` 시트 1행 = `CustService` 객체 1개.

**필드 분류** (45개 + 가입 상품 List):
- 식별: `svcMgmtNum`, `svcNum`
- 고객: `age`, `custTypCd`, `custDtlTypCd`, `nmCustNum`
- 서비스: `svcCd`, `svcTypCd`, `svcStCd`, `svcChgRsnCd`
- 요금제: `feeProdId`, `relFeeProdId`
- 단말: `eqpMdlCd`, `eqpSerNum`, `simSerNum`
- 가입 상품: `prodLst` (List<CustProd>) ← 가입 중인 모든 상품

### EquipmentModel
**위치**: `src/main/java/org/acme/fact/domain/EquipmentModel.java`

**역할**: 단말 모델 + 라인업 Map (`lnupItmCd → EquipmentModelLineup`).

### Fact 데이터 출처

- **로딩**: `FactDataService.@PostConstruct` 가 classpath `fact_data.xlsx` (3시트: `cust_prod`, `cust_svc`, `eqp_mdl`) 읽어서 `HashMap` 적재.
- **외부 노출**: REST API
  - `GET /api/fact/cust/listCustService` — 전체 회선 요약 목록
  - `GET /api/fact/cust/getCustService/{svcMgmtNum}` — 회선 상세 (가입 상품 포함)
  - `GET /api/fact/eqp/listEquipmentModel` — 전체 단말 요약 목록
  - `GET /api/fact/eqp/getEquipmentModel/{eqpMdlCd}` — 단말 상세 (라인업 Map 포함)
  - `GET /api/fact/eqp/getEquipmentModel/{eqpMdlCd}/items/{lnupItmCd}` — 단말 라인업 단건

---

## 6. 향후 추가될 외부모듈 mock API

고객 엑셀의 `수행조건별 조건구성정보` 시트에서 **IF_TXT 가 비어있고 외부 메소드 호출만 있는 케이스**가 다수 있음. 예:

```
COND_CONS_ID    | IF_TXT | 외부 호출
CONDCP000003094 | (비어있음) | zordms0360100.f_ordrs03f0000754_check
```

이런 케이스는 **고객이 직접 mock endpoint 를 본 레포에 작성해서 제공할 예정**. Claude 는 이 부분을 추측해서 구현하지 말 것 — 고객 코드 받아서 통합만 진행.

향후 추가 시 예상 위치: `src/main/java/org/acme/external/...` (현재 미존재)

---

## 7. 실행 방법

### 단일 인스턴스 (개발)

```bash
mvn clean compile spring-boot:run
# → http://localhost:8081
# → Swagger UI: http://localhost:8081/swagger-ui/index.html
```

### 빌드 후 실행

```bash
mvn clean package
java -jar target/skt-bamoe-poc-fact.jar
```

### 룰 엔진과 함께 실행 (e2e)

1. 본 레포: `mvn spring-boot:run` (port 8081)
2. 룰 레포 (`../SKT-BAMOE-POC-RULE`): 별도 터미널에서 기동 (port 8080)
3. 룰 엔진 호출 시 BPMN ServiceTask 가 본 레포의 `/api/fact/cust/getCustService/{svcMgmtNum}` 등을 HTTP GET

```bash
# 본 레포 fact API 호출 예시
curl http://localhost:8081/api/fact/cust/getCustService/7308834145 | jq
curl http://localhost:8081/api/fact/eqp/getEquipmentModel/A3D6 | jq
```

### Excel 시트 JSON 재덤프

```bash
python3 tools/dump_excel.py
# → tools/excel_dump/*.json
```

---

## 8. 결정사항 모음

작업 진행 중 사용자와 합의된 결정. 변경 시 이 표를 우선 갱신.

| # | 결정 | 사유 |
|---|-----|-----|
| 1 | 룰 레포 분리 (BPMN/DMN/DRL → `../SKT-BAMOE-POC-RULE`) | 책임 분리 + 본 레포는 데이터 시뮬레이터로 단순화 |
| 2 | 룰 자산은 `../tmp/skt-bamoe-poc-rule-snapshot/` 에 보존 (git 추적 밖) | 룰 레포 작업 시 참조용 |
| 3 | 외부모듈 mock API 는 **고객이 직접 작성**해서 제공 — Claude 추측 구현 금지 | 비즈니스 로직은 고객 도메인 |
| 4 | port=8081 (룰 엔진은 8080) | 충돌 회피 + 외부 시스템 시뮬레이션 일관성 |
| 5 | profile 어노테이션 (`@Profile`) 제거 + properties 통합 | 본 레포는 단일 영역만 남아 분기 불필요 |
| 6 | artifactId: `skt-bamoe-poc-fact` (이전: `your-bamoe-business-service`) | BAMOE 색깔 분리 |
| 7 | 클래스 rename: `BamoeSpringBootApplication` → `FactApiApplication`, `BamoeCorsConfig` → `FactCorsConfig` | 동일 |
| 8 | git commit/PR 에 Co-Authored-By, AI 흔적 일체 남기지 않음 | 협업 공간 (Client-Engineering-Korea) |

---

## 9. 자주 쓰는 명령

```bash
# 빌드
mvn clean compile
mvn clean package

# 실행
mvn spring-boot:run                           # 개발 모드
java -jar target/skt-bamoe-poc-fact.jar       # 패키지 후

# 호출 (port 8081)
curl http://localhost:8081/api/fact/cust/listCustService | jq
curl http://localhost:8081/api/fact/cust/getCustService/7308834145 | jq
curl http://localhost:8081/api/fact/eqp/getEquipmentModel/A3D6 | jq
open http://localhost:8081/swagger-ui/index.html

# Excel 분석
python3 tools/dump_excel.py
```

---

## 10. 변경 이력

이 CLAUDE.md 는 작업 진행에 따라 갱신됨. 결정사항이 바뀌거나 추가되면 §8 표를 우선 업데이트.

- **2026-04-28 분리**: BPMN/DMN/룰 wiring 분리 → `../tmp/skt-bamoe-poc-rule-snapshot/` 보존, fact API 전용 레포로 슬림화. artifactId/클래스 rename, profile 제거, port=8081 단일화.
- **2026-04-28 초안**: 통합 레포 시점 (fact + rule + BPMN/DMN). 자세한 내용은 snapshot 의 README/CLAUDE 백업 참조.
