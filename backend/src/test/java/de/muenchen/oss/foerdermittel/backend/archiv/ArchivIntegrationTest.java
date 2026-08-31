package de.muenchen.oss.foerdermittel.backend.archiv;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivCreateDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivResponseDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivUpdateDTO;
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
class ArchivIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private ArchivRepository archivRepository;

    @Autowired
    private ProjektRepository projektRepository;

    @Autowired
    private FoerderbereichRepository foerderbereichRepository;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

    private static final String EXISTING_PROJNR = "3325101";

    private static final long NON_EXISTING_ID = Long.MAX_VALUE;

    @BeforeEach
    void setUp() {
        archivRepository.deleteAll();

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

        final Projekt projekt = new Projekt();

        projekt.setProjnr(EXISTING_PROJNR);
        projekt.setPname("Testprojekt");
        projekt.setPstrasse("Teststraße");
        projekt.setFoerderbereich(foerderbereich);

        projektRepository.save(projekt);
    }

    @Nested
    class GetArchive {

        @Test
        void givenArchiveExists_thenReturnPageOfArchiveEntries() {

            final ArchivCreateDTO requestDTO = new ArchivCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T22:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T22:00:00Z"),
                    "Test",
                    EXISTING_PROJNR);

            restTestClient.post()
                    .uri("/archiv")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated();

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/archiv")
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
                            new ParameterizedTypeReference<List<ArchivResponseDTO>>() {
                            },
                            content -> {
                                assertThat(content).hasSize(1);

                                final ArchivResponseDTO archiv = content.getFirst();

                                assertThat(archiv.speicherDatum())
                                        .isEqualTo(OffsetDateTime.parse("2024-09-16T00:00:00Z"));
                                assertThat(archiv.speicherAkt()).isTrue();
                                assertThat(archiv.speicherRechnungen()).isFalse();
                                assertThat(archiv.mikroDatPlan())
                                        .isEqualTo(OffsetDateTime.parse("2024-09-17T00:00:00Z"));
                                assertThat(archiv.mikroDat())
                                        .isEqualTo(OffsetDateTime.parse("2024-09-18T00:00:00Z"));
                                assertThat(archiv.notizen()).isEqualTo("Test");
                                assertThat(archiv.projnr()).isEqualTo(EXISTING_PROJNR);
                            });
        }

        @Test
        void givenNoArchiveExists_thenReturnEmptyPage() {

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/archiv")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody()
                    .jsonPath("$.content")
                    .value(
                            new ParameterizedTypeReference<List<ArchivResponseDTO>>() {
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
                            .path("/archiv")
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
    class CreateArchiv {

        @Test
        void givenValidRequest_thenArchivIsCreated() {

            final ArchivCreateDTO requestDTO = new ArchivCreateDTO(
                    OffsetDateTime.parse("2024-09-15T00:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T00:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T00:00:00Z"),
                    "Archiv Test",
                    EXISTING_PROJNR);

            final ArchivResponseDTO responseDTO = restTestClient.post()
                    .uri("/archiv")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ArchivResponseDTO.class)
                    .value(response -> {

                        assertNotNull(response);

                        assertThat(response.speicherDatum())
                                .isEqualTo(requestDTO.speicherDatum());

                        assertThat(response.speicherAkt())
                                .isEqualTo(requestDTO.speicherAkt());

                        assertThat(response.speicherRechnungen())
                                .isEqualTo(requestDTO.speicherRechnungen());

                        assertThat(response.mikroDatPlan())
                                .isEqualTo(requestDTO.mikroDatPlan());

                        assertThat(response.mikroDat())
                                .isEqualTo(requestDTO.mikroDat());

                        assertThat(response.notizen())
                                .isEqualTo(requestDTO.notizen());

                        assertThat(response.projnr())
                                .isEqualTo(EXISTING_PROJNR);
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Archiv> entity = archivRepository.findById(responseDTO.id());

            assertThat(entity).isPresent();

            final Archiv archiv = entity.get();

            assertThat(archiv.getSpeicherDatum())
                    .isEqualTo(requestDTO.speicherDatum().toLocalDate());

            assertThat(archiv.getSpeicherAkt())
                    .isEqualTo(requestDTO.speicherAkt());

            assertThat(archiv.getSpeicherRechnungen())
                    .isEqualTo(requestDTO.speicherRechnungen());

            assertThat(archiv.getMikroDatPlan())
                    .isEqualTo(requestDTO.mikroDatPlan().toLocalDate());

            assertThat(archiv.getMikroDat())
                    .isEqualTo(requestDTO.mikroDat().toLocalDate());

            assertThat(archiv.getNotizen()).isEqualTo(requestDTO.notizen());
            assertThat(archiv.getProjekt()).isNotNull();
            assertThat(archiv.getProjekt().getProjnr()).isEqualTo(EXISTING_PROJNR);
        }

        @Test
        void givenProjectDoesNotExist_thenReturnInternalServerError() {

            final ArchivCreateDTO requestDTO = new ArchivCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T22:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T22:00:00Z"),
                    "Test",
                    "9999999");

            restTestClient.post()
                    .uri("/archiv")
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
                final ArchivCreateDTO requestDTO) {

            restTestClient.post()
                    .uri("/archiv")
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
                            new ArchivCreateDTO(
                                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                                    true,
                                    false,
                                    OffsetDateTime.parse("2024-09-16T22:00:00Z"),
                                    OffsetDateTime.parse("2024-09-17T22:00:00Z"),
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

            final ArchivCreateDTO requestDTO = new ArchivCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T22:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T22:00:00Z"),
                    "Test",
                    EXISTING_PROJNR);

            restTestClient.post()
                    .uri("/archiv")
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
    class UpdateArchiv {

        @Test
        void givenArchivExists_thenArchivIsUpdated() {

            final ArchivCreateDTO createDTO = new ArchivCreateDTO(
                    OffsetDateTime.parse("2024-09-15T00:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T00:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T00:00:00Z"),
                    "Alt",
                    EXISTING_PROJNR);

            final ArchivResponseDTO created = restTestClient.post()
                    .uri("/archiv")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(ArchivResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(created).isNotNull();

            final ArchivUpdateDTO updateDTO = new ArchivUpdateDTO(
                    OffsetDateTime.parse("2024-09-15T00:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T00:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T00:00:00Z"),
                    "Aktualisierte Notiz");

            final ArchivResponseDTO responseDTO = restTestClient.put()
                    .uri("/archiv/{id}", created.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(updateDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ArchivResponseDTO.class)
                    .value(response -> {

                        assertNotNull(response);
                        assertThat(response.id()).isEqualTo(created.id());

                        // ResponseDTO: LocalDate -> OffsetDateTime
                        assertThat(response.speicherDatum())
                                .isEqualTo(OffsetDateTime.parse("2024-09-15T00:00:00Z"));

                        assertThat(response.speicherAkt())
                                .isEqualTo(updateDTO.speicherAkt());

                        assertThat(response.speicherRechnungen())
                                .isEqualTo(updateDTO.speicherRechnungen());

                        assertThat(response.mikroDatPlan())
                                .isEqualTo(OffsetDateTime.parse("2024-09-16T00:00:00Z"));

                        assertThat(response.mikroDat())
                                .isEqualTo(OffsetDateTime.parse("2024-09-17T00:00:00Z"));

                        assertThat(response.notizen())
                                .isEqualTo(updateDTO.notizen());

                        assertThat(response.projnr())
                                .isEqualTo(EXISTING_PROJNR);
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Archiv> entity = archivRepository.findById(created.id());

            assertThat(entity).isPresent();

            // Entity: LocalDate
            assertThat(entity.get().getSpeicherDatum())
                    .isEqualTo(LocalDate.of(2024, 9, 15));

            assertThat(entity.get().getSpeicherAkt())
                    .isEqualTo(updateDTO.speicherAkt());

            assertThat(entity.get().getSpeicherRechnungen())
                    .isEqualTo(updateDTO.speicherRechnungen());

            assertThat(entity.get().getMikroDatPlan())
                    .isEqualTo(LocalDate.of(2024, 9, 16));

            assertThat(entity.get().getMikroDat())
                    .isEqualTo(LocalDate.of(2024, 9, 17));

            assertThat(entity.get().getNotizen())
                    .isEqualTo(updateDTO.notizen());

            assertThat(entity.get().getProjekt().getProjnr())
                    .isEqualTo(EXISTING_PROJNR);
        }

        @Test
        void givenArchivDoesNotExist_thenReturnNotFound() {

            final ArchivUpdateDTO updateDTO = new ArchivUpdateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T22:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T22:00:00Z"),
                    "Test");

            restTestClient.put()
                    .uri("/archiv/{id}", NON_EXISTING_ID)
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

            final ArchivCreateDTO createDTO = new ArchivCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T22:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T22:00:00Z"),
                    "Test",
                    EXISTING_PROJNR);

            final ArchivResponseDTO created = restTestClient.post()
                    .uri("/archiv")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(ArchivResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(created).isNotNull();

            final ArchivUpdateDTO updateDTO = new ArchivUpdateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T22:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T22:00:00Z"),
                    "Test");

            restTestClient.put()
                    .uri("/archiv/{id}", created.id())
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
    class DeleteArchiv {

        @Test
        void givenArchivExists_thenArchivIsDeleted() {

            final ArchivCreateDTO createDTO = new ArchivCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T22:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T22:00:00Z"),
                    "Test",
                    EXISTING_PROJNR);

            final ArchivResponseDTO created = restTestClient.post()
                    .uri("/archiv")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(ArchivResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(created).isNotNull();

            restTestClient.delete()
                    .uri("/archiv/{id}", created.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk();

            assertThat(archivRepository.findById(created.id()))
                    .isEmpty();
        }

        @Test
        void givenArchivDoesNotExist_thenReturnNotFound() {

            restTestClient.delete()
                    .uri("/archiv/{id}", NON_EXISTING_ID)
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

            final ArchivCreateDTO createDTO = new ArchivCreateDTO(
                    OffsetDateTime.parse("2024-09-15T22:00:00Z"),
                    true,
                    false,
                    OffsetDateTime.parse("2024-09-16T22:00:00Z"),
                    OffsetDateTime.parse("2024-09-17T22:00:00Z"),
                    "Test",
                    EXISTING_PROJNR);

            final ArchivResponseDTO created = restTestClient.post()
                    .uri("/archiv")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(ArchivResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(created).isNotNull();

            restTestClient.delete()
                    .uri("/archiv/{id}", created.id())
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }
}
