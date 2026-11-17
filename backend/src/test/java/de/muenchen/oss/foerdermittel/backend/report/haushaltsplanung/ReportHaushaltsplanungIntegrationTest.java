package de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.formcontext.ReportHaushaltsplanungFormContext;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@ActiveProfiles(profiles = { SPRING_TEST_PROFILE })
@Import(TestSecurityConfiguration.class)
class ReportHaushaltsplanungIntegrationTest {

    private static final String REPORT_PATH = "/report/haushaltsplanung/";
    private static final String FORM_CONTEXT_PATH = "/report/haushaltsplanung/form-context";

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

    @Nested
    class GetReportHaushaltsplanung {

        @Test
        void givenValidParameters_thenReturnFile() {
            final RestTestClient.ResponseSpec response = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(REPORT_PATH)
                            .queryParam("haushaltsjahr", "2026")
                            .queryParam("sort", "PROJEKTNUMMER")
                            .queryParam("type", "PDF")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange();

            response.expectStatus().is5xxServerError();
        }

        @Test
        void givenMissingRequiredParameters_thenReturnBadRequest() {
            restTestClient.get()
                    .uri(REPORT_PATH)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isBadRequest();
        }

        @Test
        void givenMissingSort_thenReturnBadRequest() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(REPORT_PATH)
                            .queryParam("haushaltsjahr", "2026")
                            .queryParam("type", "PDF")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isBadRequest();
        }

        @Test
        void givenUnknownSort_thenReturnBadRequest() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(REPORT_PATH)
                            .queryParam("haushaltsjahr", "2026")
                            .queryParam("sort", "UNKNOWN")
                            .queryParam("type", "PDF")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isBadRequest();
        }

        @Test
        void givenInvalidHaushaltsjahr_thenReturnBadRequest() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(REPORT_PATH)
                            .queryParam("haushaltsjahr", "20AB")
                            .queryParam("sort", "PROJEKTNUMMER")
                            .queryParam("type", "PDF")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }

    @Nested
    class GetReportHaushaltsplanungFormContext {

        @Test
        void givenRequest_thenReturnFormContext() {
            final ReportHaushaltsplanungFormContext result = restTestClient.get()
                    .uri(FORM_CONTEXT_PATH)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ReportHaushaltsplanungFormContext.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(result).isNotNull();
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    arguments("admin", HttpStatus.OK),
                    arguments("sachbearbeitung", HttpStatus.OK),
                    arguments("sachbearbeitunghaushalt", HttpStatus.OK),
                    arguments("no-role", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            restTestClient.get()
                    .uri(FORM_CONTEXT_PATH)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }
    }
}
