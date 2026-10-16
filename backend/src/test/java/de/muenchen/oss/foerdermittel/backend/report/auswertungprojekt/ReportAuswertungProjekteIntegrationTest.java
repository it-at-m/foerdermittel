package de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammRepository;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichRepository;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.HauptabschnittRepository;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportAuswertungProjektFormContext;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.Siedlungsgebiet;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietRepository;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkRepository;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittRepository;
import java.math.BigDecimal;
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
public class ReportAuswertungProjekteIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

    @Autowired
    private ProjektRepository projektRepository;

    @Autowired
    private FoerderbereichRepository foerderbereichRepository;

    @Autowired
    private StadtbezirkRepository stadtbezirkRepository;

    @Autowired
    private UnterabschnittRepository unterabschnittRepository;

    @Autowired
    private HauptabschnittRepository hauptabschnittRepository;

    @Autowired
    private KurzbezeichnungRepository kurzbezeichnungRepository;

    @Autowired
    private SiedlungsgebietRepository siedlungsgebietRepository;

    @Autowired
    private BauprogrammRepository bauprogrammRepository;

    @Nested
    class GetReportAuswertungProjekte {

        private static final String EXISTING_JAHR = "2026";
        private static final String VALID_SORT = "STADTBEZIRK_STRASSE";
        private static final String VALID_TYPE = "PDF";

        @BeforeEach
        void setUp() {
            final Foerderbereich foerderbereich = new Foerderbereich(BigDecimal.valueOf(99), "Test", false, false, false, false);
            foerderbereichRepository.save(foerderbereich);

            final Stadtbezirk stadtbezirk = new Stadtbezirk(BigDecimal.valueOf(99), "Test");
            stadtbezirkRepository.save(stadtbezirk);

            final Hauptabschnitt hauptabschnitt = new Hauptabschnitt("ha", "bz2");
            hauptabschnittRepository.save(hauptabschnitt);

            final Unterabschnitt unterabschnitt = new Unterabschnitt("L", "Test", hauptabschnitt);
            unterabschnittRepository.save(unterabschnitt);

            final Kurzbezeichnung kurzbezeichnung = new Kurzbezeichnung("KB1", "Test");
            kurzbezeichnungRepository.save(kurzbezeichnung);

            final Siedlungsgebiet siedlungsgebiet = new Siedlungsgebiet(BigDecimal.ONE, "Test");
            siedlungsgebietRepository.save(siedlungsgebiet);

            final Bauprogramm bauprogramm = new Bauprogramm(BigDecimal.ONE, "Test");
            bauprogrammRepository.save(bauprogramm);

            projektRepository.save(
                    new Projekt(
                            "P-123",
                            "Projektname",
                            "Projektstraße 1",
                            EXISTING_JAHR,
                            foerderbereich,
                            stadtbezirk,
                            unterabschnitt,
                            kurzbezeichnung,
                            siedlungsgebiet,
                            bauprogramm,
                            Krisofp.K));
        }

        @Test
        void givenMissingArguments_thenReturnBadRequest() {
            restTestClient.get()
                    .uri("/report/auswertungprojekte")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus()
                    .isBadRequest();
        }

        @Test
        void givenInvalidYear_thenReturnBadRequest() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/auswertungprojekte")
                            .queryParam("jahr", "NOTFOUND")
                            .queryParam("sort", VALID_SORT)
                            .queryParam("type", VALID_TYPE)
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus()
                    .isBadRequest();
        }

        @Test
        void givenMissingYear_thenReturnBadRequest() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/auswertungprojekte")
                            .queryParam("sort", VALID_SORT)
                            .queryParam("type", VALID_TYPE)
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus()
                    .isBadRequest();
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(
                final String role,
                final HttpStatus httpStatus) {

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/auswertungprojekte/form-context")
                            .build())
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    Arguments.of("admin", HttpStatus.OK),
                    Arguments.of("sachbearbeitung", HttpStatus.OK),
                    Arguments.of("sachbearbeitunghaushalt", HttpStatus.OK),
                    Arguments.of("no-role", HttpStatus.FORBIDDEN));
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    Arguments.of(
                            "jahr invalid format",
                            "NOTFOUND",
                            VALID_SORT,
                            VALID_TYPE),
                    Arguments.of(
                            "jahr missing",
                            null,
                            VALID_SORT,
                            VALID_TYPE),
                    Arguments.of(
                            "sort missing",
                            EXISTING_JAHR,
                            null,
                            VALID_TYPE),
                    Arguments.of(
                            "type missing",
                            EXISTING_JAHR,
                            VALID_SORT,
                            null));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final String jahr,
                final String sort,
                final String type) {

            restTestClient.get()
                    .uri(uriBuilder -> {
                        uriBuilder.path("/report/auswertungprojekte");

                        if (jahr != null) {
                            uriBuilder.queryParam("jahr", jahr);
                        }
                        if (sort != null) {
                            uriBuilder.queryParam("sort", sort);
                        }
                        if (type != null) {
                            uriBuilder.queryParam("type", type);
                        }

                        return uriBuilder.build();
                    })
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isBadRequest();
        }
    }

    @Nested
    class GetReportAuswertungProjekteFormContext {

        @Test
        void givenRequest_thenReturnFormContext() {
            // When
            final ReportAuswertungProjektFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/report/auswertungprojekte/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ReportAuswertungProjektFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
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
                            .path("/report/auswertungprojekte/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }
}
