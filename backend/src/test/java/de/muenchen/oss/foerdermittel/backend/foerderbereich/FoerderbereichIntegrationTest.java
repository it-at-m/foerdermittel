package de.muenchen.oss.foerdermittel.backend.foerderbereich;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestConstants;
import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichCreateDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichResponseDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichUpdateDTO;
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
class FoerderbereichIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestConstants.TESTCONTAINERS_POSTGRES_IMAGE));

    private static final int EXISTING_ID = 1;
    private static final int NON_EXISTING_ID = 2;

    @Autowired
    private FoerderbereichRepository foerderbereichRepository;

    @BeforeEach
    public void setUp() {
        foerderbereichRepository.deleteAll();
        final Foerderbereich exampleEntity = new Foerderbereich(new BigDecimal(EXISTING_ID), "Test", false, true, false, true);
        foerderbereichRepository.save(exampleEntity);
    }

    @Nested
    class GetFoerderbereiche {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/foerderbereiche")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.content")
                    .value(new ParameterizedTypeReference<List<FoerderbereichResponseDTO>>() {
                    }, content -> assertThat(content.size()).isEqualTo(1));
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    Arguments.of("admin", HttpStatus.OK),
                    Arguments.of("sachbearbeitung", HttpStatus.OK),
                    Arguments.of("sachbearbeitunghaushalt", HttpStatus.OK));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/foerderbereiche")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class CreateFoerderbereich {

        @Test
        void givenEntityNotExists_thenEntityIsSaved() {
            final FoerderbereichCreateDTO requestDTO = new FoerderbereichCreateDTO(NON_EXISTING_ID, "Test", true, false, true, false);

            final FoerderbereichResponseDTO responseDTO = restTestClient.post()
                    .uri("/foerderbereiche")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(FoerderbereichResponseDTO.class)
                    .value(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.fb()).isEqualTo(requestDTO.fb());
                        assertThat(response.bezeichnung()).isEqualTo(requestDTO.bezeichnung());
                        assertThat(response.finanzausgleich()).isEqualTo(requestDTO.finanzausgleich());
                        assertThat(response.jahresstatistik()).isEqualTo(requestDTO.jahresstatistik());
                        assertThat(response.kindergarten()).isEqualTo(requestDTO.kindergarten());
                        assertThat(response.nichtRelevant()).isEqualTo(requestDTO.nichtRelevant());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Foerderbereich> entity = foerderbereichRepository.findById(BigDecimal.valueOf(Integer.parseInt(responseDTO.id())));
            assertThat(entity).isPresent();
            assertThat(entity.get().getFb().intValue()).isEqualTo(requestDTO.fb());
            assertThat(entity.get().getBezeichnung()).isEqualTo(requestDTO.bezeichnung());
            assertThat(entity.get().getFinanzausgleich()).isEqualTo(requestDTO.finanzausgleich());
            assertThat(entity.get().getJahresstatistik()).isEqualTo(requestDTO.jahresstatistik());
            assertThat(entity.get().getKindergarten()).isEqualTo(requestDTO.kindergarten());
            assertThat(entity.get().getNichtRelevant()).isEqualTo(requestDTO.nichtRelevant());
        }

        @Test
        void givenEntityAlreadyExists_thenReturnConflict() {
            final FoerderbereichCreateDTO requestDTO = new FoerderbereichCreateDTO(EXISTING_ID, "Test", true, false, true, false);

            restTestClient.post()
                    .uri("/foerderbereiche")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.CONFLICT);
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "foerderbereich too low",
                            new FoerderbereichCreateDTO(-1, "Test", true, false, true, false)),
                    arguments(
                            "foerderbereich missing",
                            new FoerderbereichCreateDTO(null, "Test", true, false, true, false)),
                    arguments(
                            "bezeichnung missing",
                            new FoerderbereichCreateDTO(2, null, true, false, true, false)),
                    arguments(
                            "finanzausgleich missing",
                            new FoerderbereichCreateDTO(2, "Test", null, false, true, false)),
                    arguments(
                            "jahresstatistik missing",
                            new FoerderbereichCreateDTO(2, "Test", true, null, true, false)),
                    arguments(
                            "kindergarten missing",
                            new FoerderbereichCreateDTO(2, "Test", true, false, null, false)),
                    arguments(
                            "nichtRelevant missing",
                            new FoerderbereichCreateDTO(2, "Test", true, false, true, null)),
                    arguments(
                            "foerderbereich too high",
                            new FoerderbereichCreateDTO(100, "Test", true, false, true, false)),
                    arguments(
                            "bezeichnung too short",
                            new FoerderbereichCreateDTO(2, "", true, false, true, false)),
                    arguments(
                            "bezeichnung too long",
                            new FoerderbereichCreateDTO(2, "a".repeat(201), true, false, true, false)));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final FoerderbereichCreateDTO requestDTO) {

            restTestClient.post()
                    .uri("/foerderbereiche")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isBadRequest();
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    Arguments.of("admin", HttpStatus.CREATED),
                    Arguments.of("sachbearbeitung", HttpStatus.FORBIDDEN),
                    Arguments.of("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            final FoerderbereichCreateDTO requestDTO = new FoerderbereichCreateDTO(NON_EXISTING_ID, "Test", true, false, true, false);

            restTestClient.post()
                    .uri("/foerderbereiche")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class UpdateFoerderbereich {

        @Test
        void givenEntityExists_thenEntityIsUpdated() {
            final FoerderbereichUpdateDTO requestDTO = new FoerderbereichUpdateDTO("Test aktualisiert", true, false, true, false);

            final FoerderbereichResponseDTO responseDTO = restTestClient.put()
                    .uri("/foerderbereiche/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(FoerderbereichResponseDTO.class)
                    .value(theEntityResponseDTO -> {
                        assertNotNull(theEntityResponseDTO);
                        assertThat(theEntityResponseDTO.id()).isEqualTo(String.valueOf(EXISTING_ID));
                        assertThat(theEntityResponseDTO.bezeichnung()).isEqualTo(requestDTO.bezeichnung());
                        assertThat(theEntityResponseDTO.finanzausgleich()).isEqualTo(requestDTO.finanzausgleich());
                        assertThat(theEntityResponseDTO.jahresstatistik()).isEqualTo(requestDTO.jahresstatistik());
                        assertThat(theEntityResponseDTO.kindergarten()).isEqualTo(requestDTO.kindergarten());
                        assertThat(theEntityResponseDTO.nichtRelevant()).isEqualTo(requestDTO.nichtRelevant());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Foerderbereich> entity = foerderbereichRepository.findById(BigDecimal.valueOf(EXISTING_ID));
            assertThat(entity).isPresent();
            assertThat(entity.get().getBezeichnung()).isEqualTo(requestDTO.bezeichnung());
            assertThat(entity.get().getFinanzausgleich()).isEqualTo(requestDTO.finanzausgleich());
            assertThat(entity.get().getJahresstatistik()).isEqualTo(requestDTO.jahresstatistik());
            assertThat(entity.get().getKindergarten()).isEqualTo(requestDTO.kindergarten());
            assertThat(entity.get().getNichtRelevant()).isEqualTo(requestDTO.nichtRelevant());
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            final FoerderbereichUpdateDTO requestDTO = new FoerderbereichUpdateDTO("Test aktualisiert", true, false, true, false);

            restTestClient.put()
                    .uri("/foerderbereiche/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isNotFound();
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "bezeichnung missing",
                            new FoerderbereichUpdateDTO(null, true, false, true, false)),
                    arguments(
                            "finanzausgleich missing",
                            new FoerderbereichUpdateDTO("Test", null, false, true, false)),
                    arguments(
                            "jahresstatistik missing",
                            new FoerderbereichUpdateDTO("Test", true, null, true, false)),
                    arguments(
                            "kindergarten missing",
                            new FoerderbereichUpdateDTO("Test", true, false, null, false)),
                    arguments(
                            "nichtRelevant missing",
                            new FoerderbereichUpdateDTO("Test", true, false, true, null)),
                    arguments(
                            "bezeichnung too short",
                            new FoerderbereichUpdateDTO("", true, false, true, false)),
                    arguments(
                            "bezeichnung too long",
                            new FoerderbereichUpdateDTO("a".repeat(201), true, false, true, false)));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final FoerderbereichUpdateDTO requestDTO) {
            restTestClient.put()
                    .uri("/foerderbereiche/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isBadRequest();
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    Arguments.of("admin", HttpStatus.OK),
                    Arguments.of("sachbearbeitung", HttpStatus.FORBIDDEN),
                    Arguments.of("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            final FoerderbereichUpdateDTO requestDTO = new FoerderbereichUpdateDTO("Test aktualisiert", true, false, true, false);

            restTestClient.put()
                    .uri("/foerderbereiche/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class DeleteFoerderbereich {

        @Test
        void givenEntityIdExists_thenEntityIsDeleted() {
            restTestClient.delete()
                    .uri("/foerderbereiche/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk();

            assertThat(foerderbereichRepository.findById(BigDecimal.valueOf(EXISTING_ID))).isEmpty();
        }

        @Test
        void givenEntityIdNotExists_thenReturnNotFound() {
            restTestClient.delete()
                    .uri("/foerderbereiche/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isNotFound();
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    Arguments.of("admin", HttpStatus.OK),
                    Arguments.of("sachbearbeitung", HttpStatus.FORBIDDEN),
                    Arguments.of("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            restTestClient.delete()
                    .uri("/foerderbereiche/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class GetFoerderbereichFormContext {

        @Test
        void givenNoEntitiesExist_thenReturnEmptyFormContext() {
            // Given
            foerderbereichRepository.deleteAll();

            // When
            final FoerderbereichFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/foerderbereiche/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(FoerderbereichFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.fbs()).isEmpty();
        }

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // When
            final FoerderbereichFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/foerderbereiche/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(FoerderbereichFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.fbs()).hasSize(1);
            assertThat(result.fbs().getFirst()).isEqualByComparingTo(String.valueOf(EXISTING_ID));
        }

        private static Stream<Arguments> authorizationMappings() {
            return Stream.of(
                    Arguments.of("admin", HttpStatus.OK),
                    Arguments.of("sachbearbeitung", HttpStatus.FORBIDDEN),
                    Arguments.of("sachbearbeitunghaushalt", HttpStatus.FORBIDDEN));
        }

        @ParameterizedTest(name = "Authorization: Role ''{0}'' -> {1}")
        @MethodSource("authorizationMappings")
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/foerderbereiche/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

}
