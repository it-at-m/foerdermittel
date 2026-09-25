package de.muenchen.oss.foerdermittel.backend.istkosten;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichRepository;
import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenCreateDTO;
import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenResponseDTO;
import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
import java.math.BigDecimal;
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
class IstkostenIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private IstkostenRepository istkostenRepository;

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
    private static final String NON_EXISTING_ID = "1234567-1990-1";

    @BeforeEach
    void setUp() {
        istkostenRepository.deleteAll();
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

    private IstkostenCreateDTO createIstkostenRequest() {
        return new IstkostenCreateDTO(
                EXISTING_PROJNR,
                new BigDecimal(2026),
                new BigDecimal(9),
                new BigDecimal(10000));
    }

    private IstkostenResponseDTO createExistingIstkosten() {
        return restTestClient.post()
                .uri("/istkosten")
                .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                .body(createIstkostenRequest())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(IstkostenResponseDTO.class)
                .returnResult()
                .getResponseBody();
    }

    @Nested
    class GetIstkostene {

        @BeforeEach
        void setUp() {
            createExistingIstkosten();
        }

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/istkosten")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.content")
                    .value(new ParameterizedTypeReference<List<IstkostenResponseDTO>>() {
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
                            .path("/istkosten")
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
    class GetIstkostenFormContext {

        @Test
        void givenNoEntitiesExist_thenReturnEmptyFormContext() {
            // Given
            istkostenRepository.deleteAll();

            // When
            final IstkostenFormContext result = restTestClient.get()
                    .uri("/istkosten/form-context")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(IstkostenFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.istkosten()).isEmpty();
        }

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // Given
            final IstkostenResponseDTO existingIstkosten = createExistingIstkosten();

            assertThat(existingIstkosten).isNotNull();
            assertThat(existingIstkosten.id()).isNotNull();

            // When
            final IstkostenFormContext result = restTestClient.get()
                    .uri("/istkosten/form-context")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(IstkostenFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.istkosten()).hasSize(1);
            assertThat(result.istkosten().getFirst().getProjnr().equals(existingIstkosten.projnr()));
            assertThat(result.istkosten().getFirst().getJahr().compareTo(existingIstkosten.jahr()));
            assertThat(result.istkosten().getFirst().getMonat().compareTo(existingIstkosten.monat()));
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
                    .uri("/istkosten/form-context")
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }

    @Nested
    class CreateIstkosten {

        @Test
        void givenValidRequest_thenIstkostenIsCreated() {
            final IstkostenCreateDTO requestDTO = createIstkostenRequest();

            final IstkostenResponseDTO responseDTO = restTestClient.post()
                    .uri("/istkosten")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(IstkostenResponseDTO.class)
                    .value(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.monat()).isEqualTo(requestDTO.monat());
                        assertThat(response.jahr()).isEqualTo(requestDTO.jahr());
                        assertThat(response.istkosten()).isEqualTo(requestDTO.istkosten());
                        assertThat(response.projnr()).isEqualTo(EXISTING_PROJNR);
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Istkosten> entity = istkostenRepository
                    .findById(new IstkostenPrimaryKey(responseDTO.projnr(), responseDTO.jahr(), responseDTO.monat()));
            assertThat(entity).isPresent();

            final Istkosten istkosten = entity.get();
            assertThat(istkosten.getIstkosten()).isEqualTo(requestDTO.istkosten());
            assertThat(istkosten.getId().getMonat()).isEqualTo(requestDTO.monat());
            assertThat(istkosten.getId().getJahr()).isEqualTo(requestDTO.jahr());
            assertThat(istkosten.getProjekt()).isNotNull();
            assertThat(istkosten.getProjekt().getProjnr()).isEqualTo(EXISTING_PROJNR);
        }

        @Test
        void givenProjectDoesNotExist_thenReturnNotFound() {
            final IstkostenCreateDTO requestDTO = new IstkostenCreateDTO("1234567", new BigDecimal(2026), new BigDecimal(10), new BigDecimal(10000));

            restTestClient.post()
                    .uri("/istkosten")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isNotFound();
        }

        @ParameterizedTest
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(final String description, final IstkostenCreateDTO requestDTO) {
            restTestClient.post()
                    .uri("/istkosten")
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
                            new IstkostenCreateDTO(
                                    null, new BigDecimal(2026), new BigDecimal(10), new BigDecimal(10000))));
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
            final IstkostenCreateDTO requestDTO = createIstkostenRequest();

            restTestClient.post()
                    .uri("/istkosten")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }

    @Nested
    class UpdateIstkosten {

        private IstkostenResponseDTO existingIstkosten;

        @BeforeEach
        void setUp() {
            existingIstkosten = createExistingIstkosten();
            assertThat(existingIstkosten).isNotNull();
        }

        @Test
        void givenIstkostenExists_thenIstkostenIsUpdated() {

            final IstkostenUpdateDTO updateDTO = new IstkostenUpdateDTO(new BigDecimal(15000));

            final IstkostenResponseDTO responseDTO = restTestClient.put()
                    .uri("/istkosten/{id}", existingIstkosten.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(updateDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(IstkostenResponseDTO.class)
                    .value(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.istkosten()).isEqualTo(updateDTO.istkosten());
                        assertThat(response.projnr()).isEqualTo(EXISTING_PROJNR);
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Optional<Istkosten> entity = istkostenRepository
                    .findById(new IstkostenPrimaryKey(responseDTO.projnr(), responseDTO.jahr(), responseDTO.monat()));

            assertThat(entity).isPresent();

            final Istkosten istkosten = entity.get();

            assertThat(istkosten.getIstkosten()).isEqualTo(updateDTO.istkosten());
            assertThat(istkosten.getProjekt().getProjnr()).isEqualTo(EXISTING_PROJNR);
        }

        @Test
        void givenIstkostenDoesNotExist_thenReturnNotFound() {

            final IstkostenUpdateDTO updateDTO = new IstkostenUpdateDTO(new BigDecimal(15000));

            restTestClient.put()
                    .uri("/istkosten/{id}", NON_EXISTING_ID)
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

            final IstkostenUpdateDTO updateDTO = new IstkostenUpdateDTO(
                    new BigDecimal(15000));

            restTestClient.put()
                    .uri("/istkosten/{id}", existingIstkosten.id())
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
    class DeleteIstkosten {

        private IstkostenResponseDTO existingIstkosten;

        @BeforeEach
        void setUp() {
            existingIstkosten = createExistingIstkosten();
            assertThat(existingIstkosten).isNotNull();
        }

        @Test
        void givenIstkostenExists_thenIstkostenIsDeleted() {

            restTestClient.delete()
                    .uri("/istkosten/{id}", existingIstkosten.id())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus()
                    .isOk();

            assertThat(
                    istkostenRepository.findById(new IstkostenPrimaryKey(existingIstkosten.projnr(), existingIstkosten.jahr(), existingIstkosten.monat()))
                            .isEmpty());
        }

        @Test
        void givenIstkostenDoesNotExist_thenReturnNotFound() {

            restTestClient.delete()
                    .uri("/istkosten/{id}", NON_EXISTING_ID)
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
                    .uri("/istkosten/{id}", existingIstkosten.id())
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(httpStatus);
        }
    }
}
