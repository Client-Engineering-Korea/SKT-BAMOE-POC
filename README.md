# SKT BAMOE PoC

SKT 비즈니스 규칙 현대화를 위한 IBM BAMOE (Business Automation Manager Open Editions) 기반 PoC 프로젝트입니다.

기존 시스템의 비즈니스 로직(의사결정 규칙)을 BAMOE의 DMN/DRL로 전환하여, Spring Boot 기반의 독립적인 규칙 엔진 서비스로 구현하는 것을 목표로 합니다.

## 기술 스택

- **Runtime**: IBM BAMOE 9.4.0
- **Framework**: Spring Boot 3.5
- **Language**: Java 17
- **Build**: Maven
- **규칙 엔진**: Drools (DMN, DRL, Excel Decision Table)

## 프로젝트 구조

```
├── src/
│   ├── main/
│   │   ├── java/            # Java 소스 코드
│   │   └── resources/       # 설정 파일 및 규칙 파일 (DMN, DRL, XLSX)
│   └── test/
│       └── java/            # 테스트 코드
├── data/                    # PoC 관련 데이터 및 모듈 분석 자료
│   ├── excel_data/          # 상품 규칙 데이터 (Excel)
│   └── module_pseudocode/   # 모듈 의사코드 정의 및 분석
├── .bamoe/                  # BAMOE 배포 설정
└── pom.xml
```

## 실행 방법

### 개발 모드

```bash
mvn clean compile spring-boot:run
```

서비스 시작 후 http://0.0.0.0:8080 에서 접근 가능합니다.

Swagger UI: http://0.0.0.0:8080/swagger-ui/index.html

### 빌드 및 실행

```bash
mvn clean package
java -jar ./target/your-bamoe-business-service.jar
```
