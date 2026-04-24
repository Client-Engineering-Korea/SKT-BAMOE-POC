## 1. 디렉터리 설명

### 1.1 `01_module_definition`
- 모듈에 대한 pseudocode를 저장한 디렉터리  
- 디렉터리는 모듈의 특성을 반영하여 3가지로 구분됨  
    - `common_modules`: 공통 모듈 (예시: zordms0360110, zordms0360120 등)
    - `key_module`: 핵심 모듈 (zordms0360150 | 상품관계 확인)
    - `others`: 일반 모듈


### 1.2 `02_module_analysis`
- 앞서 각 모듈의 pseudocode를 분석한 결과를 저장한 디렉터리  
- 2가지 측면에서 분석을 진행    
    - `module_structure_{MODULE_ID}.md`: 모듈의 구조를 분석한 결과    
    - `module_statistics_{MODULE_ID}.md`: 모듈을 구성하는 IF-THEN (Rule)의 개수, DB조회 횟수 등을 분석   


### 1.3 `03_Template_UI`
- PoC 템플릿을 사용하기 위한 UI        