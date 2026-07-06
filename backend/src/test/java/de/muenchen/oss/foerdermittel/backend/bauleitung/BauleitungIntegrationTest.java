package de.muenchen.oss.foerdermittel.backend.bauleitung;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestConstants;
import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungResponseDTO;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungUpdateDTO;
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
class BauleitungIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestConstants.TESTCONTAINERS_POSTGRES_IMAGE));

    private static final String EXISTING_ID = "1";
    private static final String NON_EXISTING_ID = "2";

    @Autowired
    private BauleitungRepository bauleitungRepository;

    @BeforeEach
    public void setUp() {
        bauleitungRepository.deleteAll();
        final Bauleitung exampleEntity = new Bauleitung(EXISTING_ID, "Test");
        bauleitungRepository.save(exampleEntity);
    }

    @Nested
    class GetBauleitung {

        @Test
        void givenEntityExists_thenReturnEntity() {
            restTestClient
                    .get()
                    .uri("/bauleitungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BauleitungResponseDTO.class)
                    .value(responseDTO -> {
                        assertNotNull(responseDTO);
                        assertThat(responseDTO.id()).isEqualTo(EXISTING_ID);
                    });
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            restTestClient
                    .get()
                    .uri("/bauleitungen/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isNotFound();
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
            restTestClient
                    .get()
                    .uri("/bauleitungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class GetBauleitungen {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/bauleitungen")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.content")
                    .value(new ParameterizedTypeReference<List<BauleitungResponseDTO>>() {
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
                            .path("/bauleitungen")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class CreateBauleitung {

        @Test
        void givenEntityNotExists_thenEntityIsSaved() {
            final BauleitungCreateDTO requestDTO = new BauleitungCreateDTO(NON_EXISTING_ID, "Test");

            final BauleitungResponseDTO responseDTO = restTestClient.post()
                    .uri("/bauleitungen")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BauleitungResponseDTO.class)
                    .value(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.bauleitung()).isEqualTo(requestDTO.bauleitung());
                        assertThat(response.bezeichnung()).isEqualTo(requestDTO.bezeichnung());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Bauleitung> entity = bauleitungRepository.findById(responseDTO.id());
            assertThat(entity).isPresent();
            assertThat(entity.get().getBauleitung()).isEqualTo(requestDTO.bauleitung());
            assertThat(entity.get().getBezeichnung()).isEqualTo(requestDTO.bezeichnung());
        }

        @Test
        void givenEntityAlreadyExists_thenReturnConflict() {
            final BauleitungCreateDTO requestDTO = new BauleitungCreateDTO(EXISTING_ID, "Test");

            restTestClient.post()
                    .uri("/bauleitungen")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.CONFLICT);
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "bauleitung not uppercase",
                            new BauleitungCreateDTO("a", "Test")),
                    arguments(
                            "bauleitung too long",
                            new BauleitungCreateDTO("AB", "Test")),
                    arguments(
                            "bezeichnung too short",
                            new BauleitungCreateDTO("9", "")),
                    arguments(
                            "bezeichnung too long",
                            new BauleitungCreateDTO("A", "a".repeat(201))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final BauleitungCreateDTO requestDTO) {

            restTestClient.post()
                    .uri("/bauleitungen")
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
            final BauleitungCreateDTO requestDTO = new BauleitungCreateDTO(NON_EXISTING_ID, "Test");

            restTestClient.post()
                    .uri("/bauleitungen")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class UpdateBauleitung {

        @Test
        void givenEntityExists_thenEntityIsUpdated() {
            final BauleitungUpdateDTO requestDTO = new BauleitungUpdateDTO("Test aktualisiert");

            final BauleitungResponseDTO responseDTO = restTestClient.put()
                    .uri("/bauleitungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BauleitungResponseDTO.class)
                    .value(theEntityResponseDTO -> {
                        assertNotNull(theEntityResponseDTO);
                        assertThat(theEntityResponseDTO.id()).isEqualTo(EXISTING_ID);
                        assertThat(theEntityResponseDTO.bezeichnung()).isEqualTo(requestDTO.bezeichnung());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Bauleitung> entity = bauleitungRepository.findById(EXISTING_ID);
            assertThat(entity).isPresent();
            assertThat(entity.get().getBezeichnung()).isEqualTo(requestDTO.bezeichnung());
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            final BauleitungUpdateDTO requestDTO = new BauleitungUpdateDTO("Test aktualisiert");

            restTestClient.put()
                    .uri("/bauleitungen/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isNotFound();
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "bezeichnung too short",
                            new BauleitungUpdateDTO("")),
                    arguments(
                            "bezeichnung too long",
                            new BauleitungUpdateDTO("a".repeat(201))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final BauleitungUpdateDTO requestDTO) {
            restTestClient.put()
                    .uri("/bauleitungen/{id}", EXISTING_ID)
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
            final BauleitungUpdateDTO requestDTO = new BauleitungUpdateDTO("Test aktualisiert");

            restTestClient.put()
                    .uri("/bauleitungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class DeleteBauleitung {

        @Test
        void givenEntityIdExists_thenEntityIsDeleted() {
            restTestClient.delete()
                    .uri("/bauleitungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk();

            assertThat(bauleitungRepository.findById(EXISTING_ID).isEmpty());
        }

        @Test
        void givenEntityIdNotExists_thenReturnNotFound() {
            restTestClient.delete()
                    .uri("/bauleitungen/{id}", NON_EXISTING_ID)
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
                    .uri("/bauleitungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class GetBauleitungFormContext {

        @Test
        void givenNoEntitiesExist_thenReturnEmptyFormContext() {
            // Given
            bauleitungRepository.deleteAll();

            // When
            final BauleitungFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/bauleitungen/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BauleitungFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.bauleitungen()).isEmpty();
        }

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // When
            final BauleitungFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/bauleitungen/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BauleitungFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.bauleitungen()).hasSize(1);
            assertThat(result.bauleitungen().getFirst()).isEqualTo(EXISTING_ID);
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
                            .path("/bauleitungen/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

}
