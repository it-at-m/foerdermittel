
package de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
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

    private static final String REPORT_PATH = "/report/fortsetzungsantrag/";
    private static final String FORM_CONTEXT_PATH = "/report/fortsetzungsantrag/form-context";

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

    @Nested
    class GetReportFortsetzungsantrag {

        @Test
        void givenMissingType_thenReturnBadRequest() {
            restTestClient.get()
                    .uri(REPORT_PATH)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isBadRequest();
        }

        @Test
        void givenUnknownStadtbezirksliste_thenReturnNotFound() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(REPORT_PATH)
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
                            .path(REPORT_PATH)
                            .queryParam("type", "PDF")
                            .queryParam("bez", "99")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isNotFound();
        }
    }

    @Nested
    class GetReportFortsetzungsantragFormContext {

        @Test
        void givenValidRequest_thenReturnOk() {
            restTestClient.get()
                    .uri(FORM_CONTEXT_PATH)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk();
        }
    }
}
