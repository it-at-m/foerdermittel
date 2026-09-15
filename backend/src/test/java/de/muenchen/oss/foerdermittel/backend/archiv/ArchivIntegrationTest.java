package de.muenchen.oss.foerdermittel.backend.archiv;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
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

    private ArchivCreateDTO createArchivRequest() {
        return new ArchivCreateDTO(
                LocalDate.of(2026, 1, 1),
                true,
                false,
                LocalDate.of(2026, 1, 2),
                LocalDate.of(2026, 1, 3),
                "Test",
                EXISTING_PROJNR);
    }

    private ArchivResponseDTO createExistingArchiv() {
        return restTestClient.post()
                .uri("/archiv")
                .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                .body(createArchivRequest())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(ArchivResponseDTO.class)
                .returnResult()
                .getResponseBody();
    }

    @Nested
    class GetArchive {

        @BeforeEach
        void setUpArchiv() {
            createExistingArchiv();
        }

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/archiv")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.content")
                    .value(new ParameterizedTypeReference<List<ArchivResponseDTO>>() {
                    }, content -> assertThat(content.size()).isEqualTo(1));
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
    class GetArchiveFormContext {

        @Test
        void givenNoEntitiesExist_thenReturnEmptyFormContext() {
            // Given
            archivRepository.deleteAll();

            // When
            final ArchivFormContext result = restTestClient.get()
                    .uri("/archiv/form-context")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ArchivFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.archivId()).isEmpty();
        }

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // Given
            final ArchivResponseDTO existingArchiv = createExistingArchiv();

            assertThat(existingArchiv).isNotNull();
            assertThat(existingArchiv.id()).isNotNull();

            // When
            final ArchivFormContext result = restTestClient.get()
                    .uri("/archiv/form-context")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ArchivFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.archivId()).hasSize(1);
            assertThat(result.archivId().getFirst()).isEqualTo(Long.valueOf(existingArchiv.id()));
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    arguments("admin", HttpStatus.OK),
                    arguments("sachbearbeitung", HttpStatus.FORBIDDEN),
                    arguments("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN),
                    arguments("no-role", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(
                final String role,
                final HttpStatus httpStatus) {

            restTestClient.get()
                    .uri("/archiv/form-context")
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
            final ArchivCreateDTO requestDTO = createArchivRequest();

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
                        assertThat(response).isNotNull();
                        assertThat(response.speicherDatum()).isEqualTo(requestDTO.speicherDatum());
                        assertThat(response.speicherAkt()).isEqualTo(requestDTO.speicherAkt());
                        assertThat(response.speicherRechnungen()).isEqualTo(requestDTO.speicherRechnungen());
                        assertThat(response.mikroDatPlan()).isEqualTo(requestDTO.mikroDatPlan());
                        assertThat(response.mikroDat()).isEqualTo(requestDTO.mikroDat());
                        assertThat(response.notizen()).isEqualTo(requestDTO.notizen());
                        assertThat(response.projnr()).isEqualTo(EXISTING_PROJNR);
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Archiv> entity = archivRepository.findById(Long.valueOf(responseDTO.id()));
            assertThat(entity).isPresent();

            final Archiv archiv = entity.get();
            assertThat(archiv.getSpeicherDatum()).isEqualTo(requestDTO.speicherDatum());
            assertThat(archiv.getSpeicherAkt()).isEqualTo(requestDTO.speicherAkt());
            assertThat(archiv.getSpeicherRechnungen()).isEqualTo(requestDTO.speicherRechnungen());
            assertThat(archiv.getMikroDatPlan()).isEqualTo(requestDTO.mikroDatPlan());
            assertThat(archiv.getMikroDat()).isEqualTo(requestDTO.mikroDat());
            assertThat(archiv.getNotizen()).isEqualTo(requestDTO.notizen());
            assertThat(archiv.getProjekt()).isNotNull();
            assertThat(archiv.getProjekt().getProjnr()).isEqualTo(EXISTING_PROJNR);
        }

        @Test
        void givenProjectDoesNotExist_thenReturnNotFound() {
            final ArchivCreateDTO requestDTO = new ArchivCreateDTO(
                    LocalDate.of(2026, 1, 1),
                    true,
                    false,
                    LocalDate.of(2026, 1, 2),
                    LocalDate.of(2026, 1, 3),
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
        void givenInvalidInput_thenReturnBadRequest(final String description, final ArchivCreateDTO requestDTO) {
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
                                    LocalDate.of(2026, 1, 1),
                                    true,
                                    false,
                                    LocalDate.of(2026, 1, 2),
                                    LocalDate.of(2026, 1, 3),
                                    "Test",
                                    null)));
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    arguments("admin", HttpStatus.CREATED),
                    arguments("sachbearbeitung", HttpStatus.FORBIDDEN),
                    arguments("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN),
                    arguments("no-role", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            final ArchivCreateDTO requestDTO = createArchivRequest();

            restTestClient.post()
                    .uri("/archiv")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }

    @Nested
    class UpdateArchiv {

        private ArchivResponseDTO existingArchiv;

        @BeforeEach
        void setUpArchiv() {
            existingArchiv = createExistingArchiv();
            assertThat(existingArchiv).isNotNull();
        }

        @Test
        void givenArchivExists_thenArchivIsUpdated() {

            final ArchivUpdateDTO updateDTO = new ArchivUpdateDTO(
                    LocalDate.of(2026, 1, 2),
                    true,
                    false,
                    LocalDate.of(2026, 1, 3),
                    LocalDate.of(2026, 1, 4),
                    "Aktualisierte Notiz");

            final ArchivResponseDTO responseDTO = restTestClient.put()
                    .uri("/archiv/{id}", existingArchiv.id())
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
                        assertThat(response).isNotNull();
                        assertThat(response.id()).isEqualTo(existingArchiv.id());
                        assertThat(response.speicherDatum()).isEqualTo(updateDTO.speicherDatum());
                        assertThat(response.speicherAkt()).isEqualTo(updateDTO.speicherAkt());
                        assertThat(response.speicherRechnungen()).isEqualTo(updateDTO.speicherRechnungen());
                        assertThat(response.mikroDatPlan()).isEqualTo(updateDTO.mikroDatPlan());
                        assertThat(response.mikroDat()).isEqualTo(updateDTO.mikroDat());
                        assertThat(response.notizen()).isEqualTo(updateDTO.notizen());
                        assertThat(response.projnr()).isEqualTo(EXISTING_PROJNR);
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Archiv> entity = archivRepository.findById(Long.valueOf(existingArchiv.id()));

            assertThat(entity).isPresent();

            final Archiv archiv = entity.get();

            assertThat(archiv.getSpeicherDatum()).isEqualTo(updateDTO.speicherDatum());
            assertThat(archiv.getSpeicherAkt()).isEqualTo(updateDTO.speicherAkt());
            assertThat(archiv.getSpeicherRechnungen()).isEqualTo(updateDTO.speicherRechnungen());
            assertThat(archiv.getMikroDatPlan()).isEqualTo(updateDTO.mikroDatPlan());
            assertThat(archiv.getMikroDat()).isEqualTo(updateDTO.mikroDat());
            assertThat(archiv.getNotizen()).isEqualTo(updateDTO.notizen());
            assertThat(archiv.getProjekt().getProjnr()).isEqualTo(EXISTING_PROJNR);
        }

        @Test
        void givenArchivDoesNotExist_thenReturnNotFound() {

            final ArchivUpdateDTO updateDTO = new ArchivUpdateDTO(
                    LocalDate.of(2026, 1, 2),
                    true,
                    false,
                    LocalDate.of(2026, 1, 3),
                    LocalDate.of(2026, 1, 4),
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
                    arguments("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN),
                    arguments("no-role", HttpStatus.FORBIDDEN));

        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(
                final String role,
                final HttpStatus httpStatus) {

            final ArchivUpdateDTO updateDTO = new ArchivUpdateDTO(
                    LocalDate.of(2026, 1, 2),
                    true,
                    false,
                    LocalDate.of(2026, 1, 3),
                    LocalDate.of(2026, 1, 4),
                    "Test");

            restTestClient.put()
                    .uri("/archiv/{id}", existingArchiv.id())
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

        private ArchivResponseDTO existingArchiv;

        @BeforeEach
        void setUpArchiv() {
            existingArchiv = createExistingArchiv();
            assertThat(existingArchiv).isNotNull();
        }

        @Test
        void givenArchivExists_thenArchivIsDeleted() {

            restTestClient.delete()
                    .uri("/archiv/{id}", existingArchiv.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk();

            assertThat(
                    archivRepository.findById(Long.valueOf(existingArchiv.id()))).isEmpty();
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
                    arguments("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN),
                    arguments("no-role", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(
                final String role,
                final HttpStatus httpStatus) {

            restTestClient.delete()
                    .uri("/archiv/{id}", existingArchiv.id())
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }
}
