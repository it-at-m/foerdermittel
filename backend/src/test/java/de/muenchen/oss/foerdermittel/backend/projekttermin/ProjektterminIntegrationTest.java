package de.muenchen.oss.foerdermittel.backend.projekttermin;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.projekttermin.dto.ProjektterminCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekttermin.dto.ProjektterminResponseDTO;
import de.muenchen.oss.foerdermittel.backend.projekttermin.dto.ProjektterminUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkRepository;
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
public class ProjektterminIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private ProjektterminRepository projektterminRepository;

    @Autowired
    private FoerderbereichRepository foerderbereichRepository;

    @Autowired
    private StadtbezirkRepository stadtbezirkRepository;

    @Autowired
    private ProjektRepository projektRepository;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

    private static final String EXISTING_PROJNR = "3325101";

    private static final long NON_EXISTING_ID = Long.MAX_VALUE;

    @BeforeEach
    void setUp() {
        projektterminRepository.deleteAll();

        createExistingProject();
    }

    private void createExistingProject() {
        final Foerderbereich foerderbereich = new Foerderbereich();

        foerderbereich.setFb(BigDecimal.valueOf(99));
        foerderbereich.setBezeichnung("Test");
        foerderbereich.setFinanzausgleich(false);
        foerderbereich.setNichtRelevant(false);
        foerderbereich.setJahresstatistik(false);
        foerderbereich.setKindergarten(false);

        foerderbereichRepository.save(foerderbereich);

        final Stadtbezirk stadtbezirk = new Stadtbezirk();

        stadtbezirk.setStadtbezirk(BigDecimal.valueOf(99));
        stadtbezirk.setBezeichnung("Test Stadtbezirk 1");

        stadtbezirkRepository.save(stadtbezirk);

        final Projekt projekt = new Projekt();

        projekt.setProjnr(EXISTING_PROJNR);
        projekt.setPname("Testprojekt");
        projekt.setPstrasse("Teststraße");
        projekt.setFoerderbereich(foerderbereich);
        projekt.setStadtbezirk(stadtbezirk);

        projektRepository.save(projekt);
    }

    @Nested
    class GetProjekttermine {

        @Test
        void givenProjektterminExists_thenReturnPageOfProjektterminEntries() {

            final ProjektterminCreateDTO requestDTO = new ProjektterminCreateDTO(
                    OffsetDateTime.parse("2024-09-15T00:00:00Z"),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test",
                    EXISTING_PROJNR);

            restTestClient.post()
                    .uri("/projekttermin")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated();

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/projekttermin")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.content")
                    .value(
                            new ParameterizedTypeReference<List<ProjektterminResponseDTO>>() {
                            },
                            content -> {
                                assertThat(content).hasSize(1);

                                final ProjektterminResponseDTO projekttermin = content.getFirst();

                                assertThat(projekttermin.termin()).isEqualTo(OffsetDateTime.parse("2024-09-15T00:00:00Z"));
                                assertThat(projekttermin.ueberwachung()).isTrue();
                                assertThat(projekttermin.notizen()).isEqualTo("Test");
                                assertThat(projekttermin.projnr()).isEqualTo(EXISTING_PROJNR);
                            });

        }

        @Test
        void givenNoProjektterminExists_thenReturnEmptyPage() {

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/projekttermin")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody()
                    .jsonPath("$.content")
                    .value(
                            new ParameterizedTypeReference<List<ProjektterminResponseDTO>>() {
                            },
                            content -> assertThat(content).isEmpty());
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    arguments("admin", HttpStatus.OK),
                    arguments("sachbearbeitung", HttpStatus.OK),
                    arguments("sachbearbeitunghaushalt", HttpStatus.OK));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(
                final String role,
                final HttpStatus httpStatus) {

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/projekttermin")
                            .queryParam("page", "0")
                            .build())
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }

    @Nested
    class CreateProjekttermin {

        @Test
        void givenValidRequest_thenProjektterminIsCreated() {

            final ProjektterminCreateDTO requestDTO = new ProjektterminCreateDTO(
                    OffsetDateTime.parse("2024-09-15T00:00:00Z"),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Projekttermin Test",
                    EXISTING_PROJNR);

            final ProjektterminResponseDTO responseDTO = restTestClient.post()
                    .uri("/projekttermin")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProjektterminResponseDTO.class)
                    .value(response -> {

                        assertNotNull(response);

                        assertThat(response.termin()).isEqualTo(requestDTO.termin());
                        assertThat(response.ueberwachung()).isEqualTo(requestDTO.ueberwachung());
                        assertThat(response.zustaendig()).isEqualTo(requestDTO.zustaendig());
                        assertThat(response.telefon()).isEqualTo(requestDTO.telefon());
                        assertThat(response.notizen()).isEqualTo(requestDTO.notizen());
                        assertThat(response.projnr()).isEqualTo(EXISTING_PROJNR);
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Projekttermin> entity = projektterminRepository.findById(responseDTO.id());

            assertThat(entity).isPresent();

            final Projekttermin projekttermin = entity.get();

            assertThat(projekttermin.getTermin()).isEqualTo(requestDTO.termin().toLocalDate());
            assertThat(projekttermin.getUeberwachung()).isEqualTo(requestDTO.ueberwachung());
            assertThat(projekttermin.getZustaendig()).isEqualTo(requestDTO.zustaendig());
            assertThat(projekttermin.getTelefon()).isEqualTo(requestDTO.telefon());
            assertThat(projekttermin.getNotizen()).isEqualTo(requestDTO.notizen());
            assertThat(projekttermin.getProjekt()).isNotNull();
            assertThat(projekttermin.getProjekt().getProjnr()).isEqualTo(EXISTING_PROJNR);
        }

        @Test
        void givenProjectDoesNotExist_thenReturnInternalServerError() {

            final ProjektterminCreateDTO requestDTO = new ProjektterminCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test",
                    "9999999");

            restTestClient.post()
                    .uri("/projekttermin")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isNotFound();
        }

        @ParameterizedTest
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final ProjektterminCreateDTO requestDTO) {

            restTestClient.post()
                    .uri("/projekttermin")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isBadRequest();
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "projnr is null",
                            new ProjektterminCreateDTO(
                                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                                    true,
                                    "Max Mustermann",
                                    "1334566",
                                    "Test",
                                    null)));
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    arguments("admin", HttpStatus.CREATED),
                    arguments("sachbearbeitung", HttpStatus.FORBIDDEN),
                    arguments("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(
                final String role,
                final HttpStatus httpStatus) {

            final ProjektterminCreateDTO requestDTO = new ProjektterminCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    "Max Mustermann",
                    "1334566",
                    "Test",
                    EXISTING_PROJNR);

            restTestClient.post()
                    .uri("/projekttermin")
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }

    @Nested
    class UpdateProjekttermin {

        @Test
        void givenProjektterminExists_thenProjektterminIsUpdated() {

            final ProjektterminCreateDTO createDTO = new ProjektterminCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    "Max Mustermann",
                    "1334566",
                    "Alt",
                    EXISTING_PROJNR);

            final ProjektterminResponseDTO created = restTestClient.post()
                    .uri("/projekttermin")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(ProjektterminResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(created).isNotNull();

            final ProjektterminUpdateDTO updateDTO = new ProjektterminUpdateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    "Max Mustermann",
                    "1334566",
                    "Aktualisierte Notiz");

            final ProjektterminResponseDTO responseDTO = restTestClient.put()
                    .uri("/projekttermin/{id}", created.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(updateDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProjektterminResponseDTO.class)
                    .value(response -> {

                        assertNotNull(response);
                        assertThat(response.id()).isEqualTo(created.id());

                        // ResponseDTO: LocalDate -> OffsetDateTime
                        assertThat(response.termin()).isEqualTo(OffsetDateTime.parse("2024-09-16T00:00:00Z"));
                        assertThat(response.ueberwachung()).isEqualTo(updateDTO.ueberwachung());
                        assertThat(response.zustaendig()).isEqualTo(updateDTO.zustaendig());
                        assertThat(response.telefon()).isEqualTo(updateDTO.telefon());
                        assertThat(response.notizen()).isEqualTo(updateDTO.notizen());
                        assertThat(response.projnr()).isEqualTo(EXISTING_PROJNR);
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Projekttermin> entity = projektterminRepository.findById(created.id());

            assertThat(entity).isPresent();

            // Entity: LocalDate
            assertThat(entity.get().getTermin()).isEqualTo(LocalDate.of(2024, 9, 16));
            assertThat(entity.get().getUeberwachung()).isEqualTo(updateDTO.ueberwachung());
            assertThat(entity.get().getZustaendig()).isEqualTo(updateDTO.zustaendig());
            assertThat(entity.get().getTelefon()).isEqualTo(updateDTO.telefon());
            assertThat(entity.get().getNotizen()).isEqualTo(updateDTO.notizen());
            assertThat(entity.get().getProjekt().getProjnr()).isEqualTo(EXISTING_PROJNR);
        }

        @Test
        void givenProjektterminDoesNotExist_thenReturnNotFound() {

            final ProjektterminUpdateDTO updateDTO = new ProjektterminUpdateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test");

            restTestClient.put()
                    .uri("/projekttermin/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(updateDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isNotFound();
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    arguments("admin", HttpStatus.OK),
                    arguments("sachbearbeitung", HttpStatus.FORBIDDEN),
                    arguments("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(
                final String role,
                final HttpStatus httpStatus) {

            final ProjektterminCreateDTO createDTO = new ProjektterminCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test",
                    EXISTING_PROJNR);

            final ProjektterminResponseDTO created = restTestClient.post()
                    .uri("/projekttermin")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(ProjektterminResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(created).isNotNull();

            final ProjektterminUpdateDTO updateDTO = new ProjektterminUpdateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test");

            restTestClient.put()
                    .uri("/projekttermin/{id}", created.id())
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .body(updateDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }

    @Nested
    class DeleteProjekttermin {

        @Test
        void givenProjektterminExists_thenProjektterminIsDeleted() {

            final ProjektterminCreateDTO createDTO = new ProjektterminCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test",
                    EXISTING_PROJNR);

            final ProjektterminResponseDTO created = restTestClient.post()
                    .uri("/projekttermin")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(ProjektterminResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(created).isNotNull();

            restTestClient.delete()
                    .uri("/projekttermin/{id}", created.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk();

            assertThat(projektterminRepository.findById(created.id()))
                    .isEmpty();
        }

        @Test
        void givenProjektterminDoesNotExist_thenReturnNotFound() {

            restTestClient.delete()
                    .uri("/projekttermin/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isNotFound();
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    arguments("admin", HttpStatus.OK),
                    arguments("sachbearbeitung", HttpStatus.FORBIDDEN),
                    arguments("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(
                final String role,
                final HttpStatus httpStatus) {

            final ProjektterminCreateDTO createDTO = new ProjektterminCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test",
                    EXISTING_PROJNR);

            final ProjektterminResponseDTO created = restTestClient.post()
                    .uri("/projekttermin")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(ProjektterminResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(created).isNotNull();

            restTestClient.delete()
                    .uri("/projekttermin/{id}", created.id())
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }
}
