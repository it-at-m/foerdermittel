package de.muenchen.oss.foerdermittel.backend.report;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichworteDTO;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportStichworteFormContext;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.Stichwortbereich;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.StichwortbereichRepository;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichFormContextDTO;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
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
public class ReportIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

    @Autowired
    private StichwortbereichRepository stichwortbereichRepository;

    @Nested
    class GetReportStichworte {

        private static final String EXISTING_ID = "TEST";

        @BeforeEach
        public void setUp() {
            stichwortbereichRepository.deleteAll();
            final Stichwortbereich exampleEntity = new Stichwortbereich(EXISTING_ID, "Test");
            stichwortbereichRepository.save(exampleEntity);
        }

        @Test
        void givenExists_thenReturnFile() {
            final RestTestClient.ResponseSpec response = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/stichwortbereiche")
                            .queryParam("bereich", EXISTING_ID)
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk();

            expectFileResponse(response);
        }

        @Test
        void givenNotExists_thenReturnNotFound() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/stichwortbereiche")
                            .queryParam("bereich", "NOTFOUND")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isNotFound();
        }

        @Test
        void givenMissingArgument_thenReturnBadRequest() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/stichwortbereiche")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isBadRequest();
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    Arguments.of("admin", HttpStatus.OK),
                    Arguments.of("sachbearbeitung", HttpStatus.OK),
                    Arguments.of("sachbearbeitunghaushalt", HttpStatus.OK),
                    Arguments.of("no-role", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/stichwortbereiche")
                            .queryParam("bereich", EXISTING_ID)
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "bereich too short",
                            new ReportStichworteDTO("")),
                    arguments(
                            "bereich too long",
                            new ReportStichworteDTO("a".repeat(31))),
                    arguments(
                            "bereich wrong symbol",
                            new ReportStichworteDTO("$")));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final ReportStichworteDTO requestDTO) {

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/stichwortbereiche")
                            .queryParam("bereich", requestDTO.bereich())
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isBadRequest();
        }

    }

    @Nested
    class GetReportStichworteFormContext {

        private static final String EXISTING_ID = "TEST";
        private static final Stichwortbereich EXAMPLE_ENTITY = new Stichwortbereich(EXISTING_ID, "Test");

        @BeforeEach
        public void setUp() {
            stichwortbereichRepository.deleteAll();
            stichwortbereichRepository.save(EXAMPLE_ENTITY);
        }

        @Test
        void givenNoEntitiesExist_thenReturnEmptyFormContext() {
            // Given
            stichwortbereichRepository.deleteAll();

            // When
            final ReportStichworteFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/stichwortbereiche/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ReportStichworteFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.bereiche()).isEmpty();
        }

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // Given
            final StichwortbereichFormContextDTO expected = new StichwortbereichFormContextDTO(EXAMPLE_ENTITY.getBereich(), EXAMPLE_ENTITY.getBezeichnung());

            // When
            final ReportStichworteFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/stichwortbereiche/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ReportStichworteFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.bereiche()).hasSize(1);
            assertThat(result.bereiche().getFirst()).isEqualTo(expected);
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    Arguments.of("admin", HttpStatus.OK),
                    Arguments.of("sachbearbeitung", HttpStatus.OK),
                    Arguments.of("sachbearbeitunghaushalt", HttpStatus.OK),
                    Arguments.of("no-role", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/stichwortbereiche/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    /**
     * Utility expectation that checks for non JSON, plain or Octet content type, validates a body
     * exists and checks the correct headers.
     *
     * @param response Response retrieved using a {@link WebTestClient}
     */
    private static void expectFileResponse(RestTestClient.ResponseSpec response) {
        response
                .expectHeader()
                .value(HttpHeaders.CONTENT_DISPOSITION, value -> assertThat(ContentDisposition.parse(value).getType())
                        .isEqualTo("attachment"))
                .expectHeader()
                .value(HttpHeaders.CONTENT_TYPE, value -> {
                    MediaType contentType = MediaType.parseMediaType(value);

                    assertThat(contentType)
                            .isNotEqualTo(MediaType.APPLICATION_JSON)
                            .isNotEqualTo(MediaType.APPLICATION_OCTET_STREAM)
                            .isNotEqualTo(MediaType.TEXT_PLAIN);
                })
                .expectBody()
                .consumeWith(result -> assertThat(result.getResponseBody())
                        .isNotNull()
                        .isNotEmpty());
    }

}
