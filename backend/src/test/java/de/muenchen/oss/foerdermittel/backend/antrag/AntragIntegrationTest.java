package de.muenchen.oss.foerdermittel.backend.antrag;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.antrag.dto.AntragCreateDTO;
import de.muenchen.oss.foerdermittel.backend.antrag.dto.AntragResponseDTO;
import de.muenchen.oss.foerdermittel.backend.antrag.dto.AntragUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammRepository;
import de.muenchen.oss.foerdermittel.backend.bewilligung.Bewilligung;
import de.muenchen.oss.foerdermittel.backend.bewilligung.BewilligungRepository;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichRepository;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.HauptabschnittRepository;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.Siedlungsgebiet;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietRepository;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkRepository;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
import org.springframework.core.ParameterizedTypeReference;
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
public class AntragIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private AntragRepository antragRepository;

    @Autowired
    private FoerderbereichRepository foerderbereichRepository;

    @Autowired
    private StadtbezirkRepository stadtbezirkRepository;

    @Autowired
    private ProjektRepository projektRepository;

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

    @Autowired
    private BewilligungRepository bewilligungRepository;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

    private static final String EXISTING_PROJNR = "3325101";
    private static final String NON_EXISTING_PROJNR = "9999999";
    private static final long NON_EXISTING_ID = Long.MAX_VALUE;
    private static final LocalDate DATUM = LocalDate.of(2025, 1, 22);

    @BeforeEach
    void setUp() {
        bewilligungRepository.deleteAll();
        antragRepository.deleteAll();

        createExistingProject();
    }

    private void createExistingProject() {
        final Foerderbereich foerderbereich = new Foerderbereich(BigDecimal.valueOf(99), "Test", false, false, false, false);
        foerderbereichRepository.save(foerderbereich);

        final Stadtbezirk stadtbezirk = new Stadtbezirk(BigDecimal.valueOf(99), "Test Stadtbezirk 1");
        stadtbezirkRepository.save(stadtbezirk);

        final Hauptabschnitt exampleHa2 = new Hauptabschnitt("ha", "bz2");
        hauptabschnittRepository.save(exampleHa2);
        final Unterabschnitt unterabschnitt = new Unterabschnitt("L", "Test", exampleHa2);
        unterabschnittRepository.save(unterabschnitt);

        final Kurzbezeichnung kurzbezeichnung = new Kurzbezeichnung("KB1", "Test");
        kurzbezeichnungRepository.save(kurzbezeichnung);

        final Siedlungsgebiet siedlungsgebiet = new Siedlungsgebiet(new BigDecimal(1), "Test");
        siedlungsgebietRepository.save(siedlungsgebiet);

        final Bauprogramm bauprogramm = new Bauprogramm(new BigDecimal(1), "Test");
        bauprogrammRepository.save(bauprogramm);

        projektRepository.save(new Projekt(EXISTING_PROJNR,
                "2023",
                foerderbereich,
                "1",
                "1",
                "Test",
                "Straße 1",
                stadtbezirk,
                unterabschnitt,
                kurzbezeichnung,
                Krisofp.K));
    }

    private static AntragCreateDTO createRequest(final String projnr, final Antragstyp antragstyp, final LocalDate antragsdatum,
            final LocalDate unbeddat, final BigDecimal asuZ) {
        return new AntragCreateDTO(projnr, antragstyp, antragsdatum, BigDecimal.valueOf(500_000), BigDecimal.valueOf(250_000),
                false, null, unbeddat, null, null, asuZ, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "Test");
    }

    private static AntragCreateDTO erstantrag() {
        return createRequest(EXISTING_PROJNR, Antragstyp.E, DATUM, null, BigDecimal.valueOf(250_000));
    }

    private static AntragUpdateDTO updateRequest(final Antragstyp antragstyp, final BigDecimal asuZ) {
        return new AntragUpdateDTO(antragstyp, DATUM, BigDecimal.valueOf(500_000), BigDecimal.valueOf(250_000),
                true, DATUM, null, null, null, asuZ, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "Geändert");
    }

    private RestTestClient.ResponseSpec post(final AntragCreateDTO request) {
        return restTestClient.post()
                .uri("/antraege")
                .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                .body(request)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
    }

    private AntragResponseDTO createExistingAntrag(final AntragCreateDTO request) {
        return post(request)
                .expectStatus()
                .isCreated()
                .expectBody(AntragResponseDTO.class)
                .returnResult()
                .getResponseBody();
    }

    @Nested
    class GetAntraege {

        @Test
        void givenPageable_thenReturnPageOfAntraege() {
            createExistingAntrag(erstantrag());

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/antraege").queryParam("page", "0").build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody()
                    .jsonPath("$.content")
                    .value(
                            new ParameterizedTypeReference<List<AntragResponseDTO>>() {
                            },
                            content -> assertThat(content).hasSize(1));
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
                    .uri("/antraege/form-context")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }

    @Nested
    class CreateAntrag {

        @Test
        void givenValidAntrag_thenCreateWithProjektAndAuditFields() {
            final AntragResponseDTO result = createExistingAntrag(erstantrag());

            assertThat(result).isNotNull();
            assertThat(result.id()).isNotNull();
            assertThat(result.projnr()).isEqualTo(EXISTING_PROJNR);
            assertThat(result.pname()).isEqualTo("Test");
            assertThat(result.antragstyp()).isEqualTo(Antragstyp.E);
            assertThat(result.anlagevon()).isEqualTo("sachbearbeitung");
            assertThat(result.anlagedatum()).isNotNull();
            assertThat(result.aenderungsdatum()).isNull();
        }

        @Test
        void givenNonExistingProjnr_thenReturnNotFound() {
            post(createRequest(NON_EXISTING_PROJNR, Antragstyp.E, DATUM, null, BigDecimal.ONE))
                    .expectStatus()
                    .isNotFound();
        }

        @Test
        void givenSecondErstantrag_thenReturnConflict() {
            createExistingAntrag(erstantrag());

            post(erstantrag())
                    .expectStatus()
                    .isEqualTo(HttpStatus.CONFLICT);
        }

        @Test
        void givenSecondFortsetzungsantrag_thenCreate() {
            createExistingAntrag(createRequest(EXISTING_PROJNR, Antragstyp.F, DATUM, null, BigDecimal.ONE));

            post(createRequest(EXISTING_PROJNR, Antragstyp.F, DATUM, null, BigDecimal.ONE))
                    .expectStatus()
                    .isCreated();
        }

        @Test
        void givenUnbedenklichkeitsantragWithoutAntragsdatum_thenCreate() {
            post(createRequest(EXISTING_PROJNR, Antragstyp.U, null, DATUM, BigDecimal.ZERO))
                    .expectStatus()
                    .isCreated();
        }

        private static Stream<Arguments> invalidRequests() {
            return Stream.of(
                    Arguments.of("U without unbeddat", createRequest(EXISTING_PROJNR, Antragstyp.U, null, null, BigDecimal.ZERO)),
                    Arguments.of("E without antragsdatum", createRequest(EXISTING_PROJNR, Antragstyp.E, null, null, BigDecimal.ONE)),
                    Arguments.of("antragsdatum without Betrag", createRequest(EXISTING_PROJNR, Antragstyp.E, DATUM, null, BigDecimal.ZERO)),
                    Arguments.of("unbedja before unbeddat", new AntragCreateDTO(EXISTING_PROJNR, Antragstyp.U, null,
                            BigDecimal.ONE, BigDecimal.ONE, false, null, DATUM, DATUM.minusDays(1), null,
                            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null)),
                    Arguments.of("unbedbis without unbedja", new AntragCreateDTO(EXISTING_PROJNR, Antragstyp.U, null,
                            BigDecimal.ONE, BigDecimal.ONE, false, null, DATUM, null, DATUM,
                            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null)),
                    Arguments.of("Betrag with more than 10 digits", createRequest(EXISTING_PROJNR, Antragstyp.E, DATUM, null,
                            new BigDecimal("12345678901"))));
        }

        @ParameterizedTest(name = "{0} -> 400")
        @MethodSource("invalidRequests")
        void givenInvalidAntrag_thenReturnBadRequest(final String name, final AntragCreateDTO request) {
            post(request)
                    .expectStatus()
                    .isBadRequest();
        }
    }

    @Nested
    class UpdateAntrag {

        @Test
        void givenValidUpdate_thenUpdateAndSetAuditFields() {
            final AntragResponseDTO existing = createExistingAntrag(erstantrag());

            final AntragResponseDTO result = restTestClient.put()
                    .uri("/antraege/{id}", existing.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(updateRequest(Antragstyp.E, BigDecimal.TEN))
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(AntragResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(result).isNotNull();
            assertThat(result.asuZ()).isEqualByComparingTo(BigDecimal.TEN);
            assertThat(result.vorzbeg()).isTrue();
            assertThat(result.anlagevon()).isEqualTo("sachbearbeitung");
            assertThat(result.aenderungvon()).isEqualTo("admin");
            assertThat(result.aenderungsdatum()).isNotNull();
        }

        @Test
        void givenChangeToExistingErstantragTyp_thenReturnConflict() {
            createExistingAntrag(erstantrag());
            final AntragResponseDTO fortsetzung = createExistingAntrag(
                    createRequest(EXISTING_PROJNR, Antragstyp.F, DATUM, null, BigDecimal.ONE));

            restTestClient.put()
                    .uri("/antraege/{id}", fortsetzung.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(updateRequest(Antragstyp.E, BigDecimal.ONE))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(HttpStatus.CONFLICT);
        }

        @Test
        void givenNonExistingId_thenReturnNotFound() {
            restTestClient.put()
                    .uri("/antraege/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(updateRequest(Antragstyp.E, BigDecimal.ONE))
                    .exchange()
                    .expectStatus()
                    .isNotFound();
        }
    }

    @Nested
    class DeleteAntrag {

        private RestTestClient.ResponseSpec delete(final Object id) {
            return restTestClient.delete()
                    .uri("/antraege/{id}", id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange();
        }

        @Test
        void givenAntragWithoutBewilligung_thenDelete() {
            final AntragResponseDTO existing = createExistingAntrag(erstantrag());

            delete(existing.id()).expectStatus().isOk();

            assertThat(antragRepository.findById(Long.valueOf(existing.id()))).isEmpty();
        }

        @Test
        void givenAntragWithBewilligung_thenReturnConflict() {
            final AntragResponseDTO existing = createExistingAntrag(erstantrag());
            bewilligungRepository.save(new Bewilligung(null, antragRepository.findById(Long.valueOf(existing.id())).orElseThrow()));

            delete(existing.id()).expectStatus().isEqualTo(HttpStatus.CONFLICT);

            assertThat(antragRepository.findById(Long.valueOf(existing.id()))).isPresent();
        }

        @Test
        void givenNonExistingId_thenReturnNotFound() {
            delete(NON_EXISTING_ID).expectStatus().isNotFound();
        }
    }
}
