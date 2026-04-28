package org.acme.rule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * PGM_ID_100 BPMN 통합 테스트.
 *
 * <p>{@code src/test/resources/scenarios/*.json} 의 시나리오 파일들을 자동 로드하여
 * {@code POST /api/rule/check} 호출 → 응답의 processable / errors[].condDtlTypId 검증.
 */
/**
 * 통합 테스트는 단일 인스턴스 (profile 미명시) 로 떠서 fact 영역 + rule 영역 둘 다 활성화.
 * BPMN ServiceTask 가 self HTTP call ({@code http://localhost:8089/api/fact/...}) 로 fact 조회.
 * 이게 가능하려면 server.port 가 fixed 여야 하므로 DEFINED_PORT 사용.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {
        "server.port=8089",
        "fact.api.base-url=http://localhost:8089"
})
@ActiveProfiles("dev")
class RuleCheckIntegrationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    static Stream<ScenarioCase> scenarios() throws IOException {
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:scenarios/*.json");
        return Arrays.stream(resources)
                .sorted(Comparator.comparing(Resource::getFilename))
                .map(RuleCheckIntegrationTest::toCase);
    }

    private static ScenarioCase toCase(Resource res) {
        try (InputStream is = res.getInputStream()) {
            JsonNode root = MAPPER.readTree(is);
            JsonNode expected = root.get("_expected");
            String processable = expected.get("processable").asText();
            String stopRule = expected.has("stopRule") && !expected.get("stopRule").isNull()
                    ? expected.get("stopRule").asText() : null;
            // _expected 제거 후 본 요청 페이로드만 추출
            ((com.fasterxml.jackson.databind.node.ObjectNode) root).remove("_expected");
            return new ScenarioCase(res.getFilename(), root, processable, stopRule);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load scenario " + res.getFilename(), e);
        }
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("scenarios")
    void shouldEvaluateScenario(ScenarioCase tc) throws IOException {
        String body = MAPPER.writeValueAsString(tc.payload);

        var response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/rule/check")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String actualProcessable = response.path("processable");
        assertThat(actualProcessable)
                .as("[%s] processable", tc.name)
                .isEqualTo(tc.expectedProcessable);

        if (tc.expectedStopRule != null) {
            assertThat(response.<java.util.List<java.util.Map<String, Object>>>path("errors"))
                    .as("[%s] errors not empty", tc.name)
                    .isNotEmpty()
                    .extracting(e -> e.get("condDtlTypId"))
                    .as("[%s] condDtlTypId contains expected stop rule", tc.name)
                    .contains(tc.expectedStopRule);
        }
    }

    record ScenarioCase(String name, JsonNode payload, String expectedProcessable, String expectedStopRule) {
        @Override
        public String toString() {
            return name + " (expected=" + expectedProcessable
                    + (expectedStopRule != null ? ", stop=" + expectedStopRule : "") + ")";
        }
    }
}
