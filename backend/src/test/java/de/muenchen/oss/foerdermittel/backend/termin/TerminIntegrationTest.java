package de.muenchen.oss.foerdermittel.backend.termin;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.archiv.ArchivFormContext;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivResponseDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkRepository;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminCreateDTO;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminResponseDTO;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminUpdateDTO;
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
public class TerminIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private TerminRepository terminRepository;

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
        terminRepository.deleteAll();

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

    final TerminCreateDTO createTerminRequest = new TerminCreateDTO(
            LocalDate.of(2024, 9, 15),
            true,
            "Max Mustermann",
            "12345678",
            "Test",
            EXISTING_PROJNR);

    private TerminResponseDTO createExistingTermin() {
            return restTestClient.post()
                    .uri("/termine")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createTerminRequest)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(TerminResponseDTO.class)
                    .returnResult()
                    .getResponseBody();
    }

    @Nested
    class GetTermin {

        @BeforeEach
        void setUp() {
            createExistingTermin();
        }

        @Test
        void givenPageable_thenReturnPageOfTerminEntries() {

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/termine")
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
                            new ParameterizedTypeReference<List<TerminResponseDTO>>() {
                            },
                            content -> {
                                assertThat(content).hasSize(1);
                            });

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
                            .path("/termine")
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
    class GetTerminFormContext {

        @Test
        void givenNoEntitiesExist_thenReturnEmptyFormContext() {
            // Given
            terminRepository.deleteAll();

            // When
            final TerminFormContext result = restTestClient.get()
                    .uri("/termine/form-context")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(TerminFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.terminID()).isEmpty();
        }

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // Given
            final TerminResponseDTO existingTermin= createExistingTermin();

            assertThat(existingTermin).isNotNull();
            assertThat(existingTermin.id()).isNotNull();

            // When
            final TerminFormContext result = restTestClient.get()
                    .uri("/termine/form-context")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(TerminFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.terminID()).hasSize(1);
            assertThat(result.terminID().getFirst()).isEqualTo(Long.valueOf(existingTermin.id()));
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
                    .uri("/termine/form-context")
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }

    @Nested
    class CreateTermin {

        @Test
        void givenValidRequest_thenTerminIsCreated() {

            final TerminCreateDTO requestDTO = new TerminCreateDTO(
                    LocalDate.of(2024, 9, 15),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "termin Test",
                    EXISTING_PROJNR);

            final TerminResponseDTO responseDTO = restTestClient.post()
                    .uri("/termine")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(TerminResponseDTO.class)
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

            final Optional<Termin> entity = terminRepository.findById(Long.valueOf(responseDTO.id()));

            assertThat(entity).isPresent();

            final Termin termin = entity.get();

            assertThat(termin.getTermin()).isEqualTo(requestDTO.termin());
            assertThat(termin.getUeberwachung()).isEqualTo(requestDTO.ueberwachung());
            assertThat(termin.getZustaendig()).isEqualTo(requestDTO.zustaendig());
            assertThat(termin.getTelefon()).isEqualTo(requestDTO.telefon());
            assertThat(termin.getNotizen()).isEqualTo(requestDTO.notizen());
            assertThat(termin.getProjekt()).isNotNull();
            assertThat(termin.getProjekt().getProjnr()).isEqualTo(EXISTING_PROJNR);
        }

        @Test
        void givenProjectDoesNotExist_thenReturnNotFound() {

            final TerminCreateDTO requestDTO = new TerminCreateDTO(
                    LocalDate.of(2024, 9, 15),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test",
                    "9999999");

            restTestClient.post()
                    .uri("/termine")
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
                final TerminCreateDTO requestDTO) {

            restTestClient.post()
                    .uri("/termine")
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
                            new TerminCreateDTO(
                                    LocalDate.of(2024, 9, 15),
                                    true,
                                    "Max Mustermann",
                                    "1334566",
                                    "Test",
                                    null)),
                    arguments(
                            "zustaendig is empty",
                            new TerminCreateDTO(
                                    LocalDate.of(2024, 9, 15),
                                    true,
                                    "",
                                    "1334566",
                                    "Test",
                                    EXISTING_PROJNR)),
                    arguments(
                            "zustaendig is too long",
                            new TerminCreateDTO(
                                    LocalDate.of(2024, 9, 15),
                                    true,
                                    "1234567890123456789012345678901",
                                    "1334566",
                                    "Test",
                                    EXISTING_PROJNR)),
                    arguments(
                            "telefon is empty",
                            new TerminCreateDTO(
                                    LocalDate.of(2024, 9, 15),
                                    true,
                                    "Max Mustermann",
                                    "",
                                    "Test",
                                    EXISTING_PROJNR)),
                    arguments(
                            "telefon is too long",
                            new TerminCreateDTO(
                                    LocalDate.of(2024, 9, 15),
                                    true,
                                    "Max Mustermann",
                                    "1234567890123456789012345678901",
                                    "Test",
                                    EXISTING_PROJNR)));
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    Arguments.of("admin", HttpStatus.CREATED),
                    Arguments.of("sachbearbeitung", HttpStatus.FORBIDDEN),
                    Arguments.of("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN),
                    Arguments.of("no-role", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(
                final String role,
                final HttpStatus httpStatus) {

            final TerminCreateDTO requestDTO = new TerminCreateDTO(
                    LocalDate.of(2024, 9, 15),
                    true,
                    "Max Mustermann",
                    "1334566",
                    "Test",
                    EXISTING_PROJNR);

            restTestClient.post()
                    .uri("/termine")
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
    class UpdateTermin {

        private TerminResponseDTO existingTermin;

        @BeforeEach
        void setUp() {

            existingTermin = createExistingTermin();
            assertThat(existingTermin).isNotNull();
        }

        @Test
        void givenTerminExists_thenTerminIsUpdated() {

            final TerminUpdateDTO updateDTO = new TerminUpdateDTO(
                    LocalDate.of(2024, 9, 15),
                    true,
                    "Max Mustermann",
                    "1334566",
                    "Aktualisierte Notiz");

            final TerminResponseDTO responseDTO = restTestClient.put()
                    .uri("/termine/{id}", existingTermin.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(updateDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(TerminResponseDTO.class)
                    .value(response -> {

                        assertNotNull(response);
                        assertThat(response.id()).isEqualTo(existingTermin.id());

                        assertThat(response.termin()).isEqualTo(updateDTO.termin());
                        assertThat(response.ueberwachung()).isEqualTo(updateDTO.ueberwachung());
                        assertThat(response.zustaendig()).isEqualTo(updateDTO.zustaendig());
                        assertThat(response.telefon()).isEqualTo(updateDTO.telefon());
                        assertThat(response.notizen()).isEqualTo(updateDTO.notizen());
                        assertThat(response.projnr()).isEqualTo(EXISTING_PROJNR);
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Termin> entity = terminRepository.findById(Long.valueOf(existingTermin.id()));

            assertThat(entity).isPresent();

            // Entity: LocalDate
            assertThat(entity.get().getTermin()).isEqualTo(LocalDate.of(2024, 9, 15));
            assertThat(entity.get().getUeberwachung()).isEqualTo(updateDTO.ueberwachung());
            assertThat(entity.get().getZustaendig()).isEqualTo(updateDTO.zustaendig());
            assertThat(entity.get().getTelefon()).isEqualTo(updateDTO.telefon());
            assertThat(entity.get().getNotizen()).isEqualTo(updateDTO.notizen());
            assertThat(entity.get().getProjekt().getProjnr()).isEqualTo(EXISTING_PROJNR);
        }

        @Test
        void givenTerminDoesNotExist_thenReturnNotFound() {

            final TerminUpdateDTO updateDTO = new TerminUpdateDTO(
                    LocalDate.of(2024, 9, 15),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test");

            restTestClient.put()
                    .uri("/termine/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(updateDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isNotFound();
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    Arguments.of("admin", HttpStatus.OK),
                    Arguments.of("sachbearbeitung", HttpStatus.FORBIDDEN),
                    Arguments.of("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN),
                    Arguments.of("no-role", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(
                final String role,
                final HttpStatus httpStatus) {

            final TerminCreateDTO createDTO = new TerminCreateDTO(
                    LocalDate.of(2024, 9, 15),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test",
                    EXISTING_PROJNR);

            final TerminResponseDTO created = restTestClient.post()
                    .uri("/termine")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(TerminResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(created).isNotNull();

            final TerminUpdateDTO updateDTO = new TerminUpdateDTO(
                    LocalDate.of(2024, 9, 15),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test");

            restTestClient.put()
                    .uri("/termine/{id}", created.id())
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
    class DeleteTermin {

        private TerminResponseDTO existingTermin;

        @BeforeEach
        void setUp() {
            existingTermin = createExistingTermin();
            assertThat(existingTermin).isNotNull();
        }

        @Test
        void givenTerminExists_thenTerminIsDeleted() {

            restTestClient.delete()
                    .uri("/termine/{id}", existingTermin.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk();

            assertThat(terminRepository.findById(Long.valueOf(existingTermin.id())))
                    .isEmpty();
        }

        @Test
        void givenTerminDoesNotExist_thenReturnNotFound() {

            restTestClient.delete()
                    .uri("/termine/{id}", NON_EXISTING_ID)
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
                    .uri("/termine/{id}", existingTermin.id())
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }
}
