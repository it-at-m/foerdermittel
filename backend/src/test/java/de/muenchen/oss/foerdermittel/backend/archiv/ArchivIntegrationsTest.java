package de.muenchen.oss.foerdermittel.backend.archiv;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestConstants;
import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivCreateDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivResponseDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
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
@ActiveProfiles(profiles = {SPRING_TEST_PROFILE})
@Import(TestSecurityConfiguration.class)
class ArchivIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestConstants.TESTCONTAINERS_POSTGRES_IMAGE));

    private static final Long EXISTING_ID = 1L;
    private static final Long NON_EXISTING_ID = 999L;
    private static final String PROJNR = "1124101";

    @Autowired
    private ArchivRepository archivRepository;

    @Autowired
    private ProjektRepository projektRepository;

    @BeforeEach
    void setUp() {
        archivRepository.deleteAll();

        final Projekt projekt = new Projekt(PROJNR, "Projektname 1");
        projektRepository.save(projekt);

        final Archiv exampleEntity = new Archiv(
                EXISTING_ID,
                projekt,
                LocalDate.of(2024, 9, 14),
                true,
                true,
                LocalDate.of(2024, 9, 16),
                LocalDate.of(2024, 9, 18),
                "Fusce tincidunt, nisl quis bibendum fermentumeee"
        );

        archivRepository.save(exampleEntity);
    }

    @Nested
    class GetArchiveintraege {

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
                    }, content -> {
                        assertThat(content.size()).isEqualTo(1);
                        assertThat(content.getFirst().id()).isEqualTo(EXISTING_ID);
                        assertThat(content.getFirst().projnr()).isEqualTo(PROJNR);
                    });
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
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }
    }

    @Nested
    class CreateArchiv {

        @Test
        void givenEntityNotExists_thenEntityIsSaved() {
            final ArchivCreateDTO requestDTO = new ArchivCreateDTO(
                    LocalDate.of(2024, 9, 25),
                    true,
                    true,
                    LocalDate.of(2024, 9, 26),
                    LocalDate.of(2024, 9, 27),
                    "Neuer Archiv-Eintrag",
                    PROJNR
            );

            final ArchivResponseDTO responseDTO = restTestClient.post()
                    .uri("/archiv")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ArchivResponseDTO.class)
                    .value(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.projnr()).isEqualTo(PROJNR);
                        assertThat(response.pname()).isEqualTo("Projektname 1");
                        assertThat(response.pstrasse()).isEqualTo(response.pstrasse());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Archiv> entity = archivRepository.findById(responseDTO.id());

            assertThat(entity).isPresent();
            assertThat(entity.get().getProjekt().getProjnr()).isEqualTo(PROJNR);
            assertThat(entity.get().getSpeicherDatum())
                    .isEqualTo(requestDTO.speicherDatum());
            assertThat(entity.get().getSpeicherAkt())
                    .isEqualTo(requestDTO.speicherAkt());
            assertThat(entity.get().getSpeicherRechnungen())
                    .isEqualTo(requestDTO.speicherRechnungen());
            assertThat(entity.get().getMikroDatPlan())
                    .isEqualTo(requestDTO.mikroDatPlan());
            assertThat(entity.get().getMikroDat())
                    .isEqualTo(requestDTO.mikroDat());
            assertThat(entity.get().getNotizen())
                    .isEqualTo(requestDTO.notizen());
        }

        @Test
        void givenProjektNotExists_thenReturnNotFound() {
            final ArchivCreateDTO requestDTO = new ArchivCreateDTO(
                    LocalDate.of(2024, 9, 25),
                    true,
                    true,
                    LocalDate.of(2024, 9, 26),
                    LocalDate.of(2024, 9, 27),
                    "Neuer Archiv-Eintrag",
                    "9999999"
            );

            restTestClient.post()
                    .uri("/archiv")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isNotFound();
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
                    LocalDate.of(2024, 9, 25),
                    true,
                    true,
                    LocalDate.of(2024, 9, 26),
                    LocalDate.of(2024, 9, 27),
                    "Neuer Archiv-Eintrag",
                    PROJNR
            );

            restTestClient.post()
                    .uri("/archiv")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }
    }

    @Nested
    class UpdateArchiv {

        @Test
        void givenEntityExists_thenEntityIsUpdated() {
            final ArchivUpdateDTO requestDTO = new ArchivUpdateDTO(
                    LocalDate.of(2026, 8, 1),
                    false,
                    false,
                    LocalDate.of(2026, 8, 2),
                    LocalDate.of(2026, 8, 3),
                    "Archiv aktualisiert",
                    PROJNR
            );

            final ArchivResponseDTO responseDTO = restTestClient.put()
                    .uri("/archiv/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ArchivResponseDTO.class)
                    .value(response -> {
                        assertNotNull(response);
                        assertThat(response.id()).isEqualTo(EXISTING_ID);
                        assertThat(response.projnr()).isEqualTo(PROJNR);
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Archiv> entity = archivRepository.findById(EXISTING_ID);

            assertThat(entity).isPresent();
            assertThat(entity.get().getSpeicherDatum())
                    .isEqualTo(requestDTO.speicherDatum());
            assertThat(entity.get().getSpeicherAkt())
                    .isEqualTo(requestDTO.speicherAkt());
            assertThat(entity.get().getSpeicherRechnungen())
                    .isEqualTo(requestDTO.speicherRechnungen());
            assertThat(entity.get().getMikroDatPlan())
                    .isEqualTo(requestDTO.mikroDatPlan());
            assertThat(entity.get().getMikroDat())
                    .isEqualTo(requestDTO.mikroDat());
            assertThat(entity.get().getNotizen())
                    .isEqualTo(requestDTO.notizen());
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            final ArchivUpdateDTO requestDTO = new ArchivUpdateDTO(
                    LocalDate.of(2026, 8, 1),
                    false,
                    false,
                    LocalDate.of(2026, 8, 2),
                    LocalDate.of(2026, 8, 3),
                    "Archiv aktualisiert",
                    PROJNR
            );

            restTestClient.put()
                    .uri("/archiv/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isNotFound();
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

            final ArchivUpdateDTO requestDTO = new ArchivUpdateDTO(
                    LocalDate.of(2026, 8, 1),
                    false,
                    false,
                    LocalDate.of(2026, 8, 2),
                    LocalDate.of(2026, 8, 3),
                    "Archiv aktualisiert",
                    PROJNR
            );

            restTestClient.put()
                    .uri("/archiv/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }
    }

    @Nested
    class DeleteArchiv {

        @Test
        void givenEntityIdExists_thenEntityIsDeleted() {
            restTestClient.delete()
                    .uri("/archiv/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk();

            assertThat(archivRepository.findById(EXISTING_ID).isEmpty()).isTrue();
        }

        @Test
        void givenEntityIdNotExists_thenReturnNotFound() {
            restTestClient.delete()
                    .uri("/archiv/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isNotFound();
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

            restTestClient.delete()
                    .uri("/archiv/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }
    }

    @Nested
    class GetArchivFormContext {

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            final ArchivFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/archiv/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ArchivFormContext.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(result).isNotNull();
            assertThat(result.archivIds()).contains(EXISTING_ID);
            assertThat(result.projekte())
                    .anyMatch(projekt -> PROJNR.equals(projekt.getProjnr()));
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

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/archiv/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }
    }
}
