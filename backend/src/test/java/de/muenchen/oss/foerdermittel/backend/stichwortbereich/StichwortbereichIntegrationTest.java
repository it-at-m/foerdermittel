package de.muenchen.oss.foerdermittel.backend.stichwortbereich;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestConstants;
import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichCreateDTO;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichResponseDTO;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichUpdateDTO;
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
public class StichwortbereichIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestConstants.TESTCONTAINERS_POSTGRES_IMAGE));

    private static final String EXISTING_ID = "L";
    private static final String NON_EXISTING_ID = "K";

    @Autowired
    private StichwortbereichRepository stichwortbereichRepository;

    @BeforeEach
    public void setUp() {
        stichwortbereichRepository.deleteAll();
        final Stichwortbereich exampleEntity = new Stichwortbereich(EXISTING_ID, "Test");
        stichwortbereichRepository.save(exampleEntity);
    }

    @Nested
    class GetStichwortbereiche {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/stichwortbereiche")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.content")
                    .value(new ParameterizedTypeReference<List<StichwortbereichResponseDTO>>() {
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
                            .path("/stichwortbereiche")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class CreateStichwortbereich {

        @Test
        void givenEntityNotExists_thenEntityIsSaved() {
            final StichwortbereichCreateDTO requestDTO = new StichwortbereichCreateDTO(NON_EXISTING_ID, "Test");

            final StichwortbereichResponseDTO responseDTO = restTestClient.post()
                    .uri("/stichwortbereiche")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(StichwortbereichResponseDTO.class)
                    .value(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.bereich()).isEqualTo(requestDTO.bereich());
                        assertThat(response.bezeichnung()).isEqualTo(requestDTO.bezeichnung());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Stichwortbereich> entity = stichwortbereichRepository.findById(responseDTO.id());
            assertThat(entity).isPresent();
            assertThat(entity.get().getBereich()).isEqualTo(requestDTO.bereich());
            assertThat(entity.get().getBezeichnung()).isEqualTo(requestDTO.bezeichnung());
        }

        @Test
        void givenEntityAlreadyExists_thenReturnConflict() {
            final StichwortbereichCreateDTO requestDTO = new StichwortbereichCreateDTO(EXISTING_ID, "Test");

            restTestClient.post()
                    .uri("/stichwortbereiche")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.CONFLICT);
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "bereiche not uppercase",
                            new StichwortbereichCreateDTO("a", "Test")),
                    arguments(
                            "bereiche too short",
                            new StichwortbereichCreateDTO("", "Test")),
                    arguments(
                            "bereiche too long",
                            new StichwortbereichCreateDTO("A".repeat(31), "Test")),
                    arguments(
                            "bezeichnung too short",
                            new StichwortbereichCreateDTO("FK-10", "")),
                    arguments(
                            "bezeichnung too long",
                            new StichwortbereichCreateDTO("A", "a".repeat(201))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final StichwortbereichCreateDTO requestDTO) {

            restTestClient.post()
                    .uri("/stichwortbereiche")
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
            final StichwortbereichCreateDTO requestDTO = new StichwortbereichCreateDTO(NON_EXISTING_ID, "Test");

            restTestClient.post()
                    .uri("/stichwortbereiche")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class UpdateStichwortbereich {

        @Test
        void givenEntityExists_thenEntityIsUpdated() {
            final StichwortbereichUpdateDTO requestDTO = new StichwortbereichUpdateDTO("Test aktualisiert");

            final StichwortbereichResponseDTO responseDTO = restTestClient.put()
                    .uri("/stichwortbereiche/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(StichwortbereichResponseDTO.class)
                    .value(theEntityResponseDTO -> {
                        assertNotNull(theEntityResponseDTO);
                        assertThat(theEntityResponseDTO.id()).isEqualTo(EXISTING_ID);
                        assertThat(theEntityResponseDTO.bezeichnung()).isEqualTo(requestDTO.bezeichnung());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Stichwortbereich> entity = stichwortbereichRepository.findById(EXISTING_ID);
            assertThat(entity).isPresent();
            assertThat(entity.get().getBezeichnung()).isEqualTo(requestDTO.bezeichnung());
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            final StichwortbereichUpdateDTO requestDTO = new StichwortbereichUpdateDTO("Test aktualisiert");

            restTestClient.put()
                    .uri("/stichwortbereiche/{id}", NON_EXISTING_ID)
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
                            new StichwortbereichUpdateDTO("")),
                    arguments(
                            "bezeichnung too long",
                            new StichwortbereichUpdateDTO("a".repeat(201))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final StichwortbereichUpdateDTO requestDTO) {
            restTestClient.put()
                    .uri("/stichwortbereiche/{id}", EXISTING_ID)
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
            final StichwortbereichUpdateDTO requestDTO = new StichwortbereichUpdateDTO("Test aktualisiert");

            restTestClient.put()
                    .uri("/stichwortbereiche/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class DeleteStichwortbereich {

        @Test
        void givenEntityIdExists_thenEntityIsDeleted() {
            restTestClient.delete()
                    .uri("/stichwortbereiche/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk();

            assertThat(stichwortbereichRepository.findById(EXISTING_ID)).isEmpty();
        }

        @Test
        void givenEntityIdNotExists_thenReturnNotFound() {
            restTestClient.delete()
                    .uri("/stichwortbereiche/{id}", NON_EXISTING_ID)
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
                    .uri("/stichwortbereiche/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class GetStichwortbereichFormContext {

        @Test
        void givenNoEntitiesExist_thenReturnEmptyFormContext() {
            // Given
            stichwortbereichRepository.deleteAll();

            // When
            final StichwortbereichFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/stichwortbereiche/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(StichwortbereichFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.bereiche()).isEmpty();
        }

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // When
            final StichwortbereichFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/stichwortbereiche/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(StichwortbereichFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.bereiche()).hasSize(1);
            assertThat(result.bereiche().getFirst()).isEqualTo(EXISTING_ID);
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
                            .path("/stichwortbereiche/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

}
