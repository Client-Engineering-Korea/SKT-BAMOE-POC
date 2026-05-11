# Repository Guidelines

## Project Scope

**SKT-BAMOE-POC** is the **customer-side data simulator** for the SKT BAMOE PoC. It exposes Excel-backed REST endpoints (`fact API`) and will host customer-provided external module mocks. The IBM BAMOE rule engine (BPMN/DMN/DRL) lives in a separate repository: [`../SKT-BAMOE-POC-RULE`](../SKT-BAMOE-POC-RULE).

This repo is intentionally a plain Spring Boot REST service — no BAMOE/Drools/Kogito dependencies.

## Project Structure & Module Organization

Java 17, Spring Boot 3.5. Code lives under `src/main/java/org/acme`:
- `fact.controller` — REST endpoints under `/api/fact/cust/*`, `/api/fact/eqp/*`
- `fact.service` — `FactDataService` loads `excel_data/fact_data.xlsx` into in-memory HashMaps at boot
- `fact.domain` — Java records (`CustService` 45 fields + product list, `EquipmentModel` with lineup map, summaries)
- `FactApiApplication`, `FactCorsConfig` — entry point + CORS

Customer reference data: `data/excel_data/` (raw `.xlsx` from SKT) and `data/module_pseudocode/`. Tooling: `tools/dump_excel.py` regenerates JSON dumps under `tools/excel_dump/`.

External-module mock APIs (future, customer-provided) will land under `src/main/java/org/acme/external/...` — do not pre-emptively scaffold them.

## Architecture Notes

- Single Spring Boot instance on port `8081`. The rule engine instance (separate repo) on port `8080` calls the fact API over HTTP.
- No Spring `@Profile` branching — the repo is now single-purpose, so profile annotations were removed.
- Excel loading happens once at startup via `@PostConstruct`. Treat the in-memory maps as read-only at runtime.
- Rule snapshot (BPMN/DMN/Java rule wiring) at the time of split is preserved at `../tmp/skt-bamoe-poc-rule-snapshot/` (outside git). Reference it when working on the rule repo, not when modifying this one.

## Build, Test, and Development Commands

- `mvn clean compile` — fast type check.
- `mvn spring-boot:run` — starts on `8081`. Swagger at `http://localhost:8081/swagger-ui/index.html`.
- `mvn clean package` — builds `target/skt-bamoe-poc-fact.jar`. Run with `java -jar target/skt-bamoe-poc-fact.jar`.
- `python3 tools/dump_excel.py` — regenerates JSON dumps from customer Excel sheets.

There are currently no automated tests in this repo (rule integration tests moved to the rule repo). Add JUnit 5 / RestAssured tests under `src/test/java` if you introduce non-trivial fact transformations.

## Coding Style & Naming Conventions

Java 17 records for immutable domain types, 4-space indentation, constructor injection. Keep controllers thin — load/transform logic stays in services. Name DTOs by domain noun (`CustService`, `EquipmentModelSummary`); keep field names aligned with the source Excel column names where reasonable so debugging stays straightforward.

## External Module Mocks (future)

The customer Excel’s `수행조건별 조건구성정보` sheet contains rows where `IF_TXT` is empty and only an external method is referenced (e.g. `zordms0360100.f_ordrs03f0000754_check`). These will be implemented as mock REST endpoints **by the customer**, not by us. When customer code arrives, integrate it under `org.acme.external` and update CLAUDE.md §6.

## Commit & Pull Request Guidelines

- Short Korean or English summaries (e.g. `update resources`, `고객가입 record 삭제`). Keep commits focused.
- **Never** add `Co-Authored-By` trailers, "Generated with Claude" footers, or any AI authorship markers. This is a shared collaboration space (`Client-Engineering-Korea`); commits and PRs must read as the human author’s work.
- PR body: describe API changes, list manual verification (curl/Swagger), link issues. Include screenshots only when Swagger output changes.
- Do not commit secrets, credentials, or temporary Office files (`~$*.xlsx`).
