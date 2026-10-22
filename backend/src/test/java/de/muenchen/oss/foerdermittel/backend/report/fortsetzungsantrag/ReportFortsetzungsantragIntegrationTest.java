package de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
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
public class ReportFortsetzungsantragIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

    /// Fortsetzungsantrag
    @Nested
    class GetReportFortsetzungsantrag {

        @Test
        void givenMissingType_thenReturnBadRequest() {
            restTestClient.get()
                    .uri("/report/fortsetzungsantrag")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isBadRequest();
        }

        @Test
        void givenUnknownStadtbezirksliste_thenReturnNotFound() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/fortsetzungsantrag")
                            .queryParam("type", "PDF")
                            .queryParam("sbl", "NOTFOUND")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isNotFound();
        }

        @Test
        void givenUnknownStadtbezirk_thenReturnNotFound() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/fortsetzungsantrag")
                            .queryParam("type", "PDF")
                            .queryParam("bez", "99")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isNotFound();
        }
    }

    /// Utility expectation that checks for non JSON, plain or Octet content type, validates a body
    /// exists and checks the correct headers.
    ///
    /// @param response Response retrieved using a [WebTestClient]
    public static void expectFileResponse(RestTestClient.ResponseSpec response) {
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
