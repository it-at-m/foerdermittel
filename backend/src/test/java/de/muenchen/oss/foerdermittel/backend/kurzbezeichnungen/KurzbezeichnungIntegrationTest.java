package de.muenchen.oss.foerdermittel.backend.kurzbezeichnungen;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestConstants;
import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungFormContext;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungRepository;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungCreateDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungResponseDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungUpdateDTO;
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
public class KurzbezeichnungIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestConstants.TESTCONTAINERS_POSTGRES_IMAGE));

    private static final String EXISTING_ID = "KB1";
    private static final String NON_EXISTING_ID = "KB2";

    @Autowired
    private KurzbezeichnungRepository kurzbezeichnungRepository;

    @BeforeEach
    public void setUp() {
        kurzbezeichnungRepository.deleteAll();
        final Kurzbezeichnung exampleEntity = new Kurzbezeichnung(EXISTING_ID, "Test");
        kurzbezeichnungRepository.save(exampleEntity);
    }

    @Nested
    class GetKurzbezeichnung {

        @Test
        void givenEntityExists_thenReturnEntity() {
            restTestClient
                    .get()
                    .uri("/kurzbezeichnungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(KurzbezeichnungResponseDTO.class)
                    .value(responseDTO -> {
                        assertNotNull(responseDTO);
                        assertThat(responseDTO.id()).isEqualTo(EXISTING_ID);
                    });
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            restTestClient
                    .get()
                    .uri("/kurzbezeichnungen/{id}", NON_EXISTING_ID)
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
                    .uri("/kurzbezeichnungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class GetKurzbezeichnungen {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/kurzbezeichnungen")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.content")
                    .value(new ParameterizedTypeReference<List<KurzbezeichnungResponseDTO>>() {
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
                            .path("/kurzbezeichnungen")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class CreateKurzbezeichnung {

        @Test
        void givenEntityNotExists_thenEntityIsSaved() {
            final KurzbezeichnungCreateDTO requestDTO = new KurzbezeichnungCreateDTO(NON_EXISTING_ID, "Test");

            final KurzbezeichnungResponseDTO responseDTO = restTestClient.post()
                    .uri("/kurzbezeichnungen")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(KurzbezeichnungResponseDTO.class)
                    .value(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.kurzbez()).isEqualTo(requestDTO.kurzbez());
                        assertThat(response.bezeichnung()).isEqualTo(requestDTO.bezeichnung());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Kurzbezeichnung> entity = kurzbezeichnungRepository.findById(responseDTO.id());
            assertThat(entity).isPresent();
            assertThat(entity.get().getKurzbez()).isEqualTo(requestDTO.kurzbez());
            assertThat(entity.get().getBezeichnung()).isEqualTo(requestDTO.bezeichnung());
        }

        @Test
        void givenEntityAlreadyExists_thenReturnConflict() {
            final KurzbezeichnungCreateDTO requestDTO = new KurzbezeichnungCreateDTO(EXISTING_ID, "Test");

            restTestClient.post()
                    .uri("/kurzbezeichnungen")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.CONFLICT);
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "kurzbez not uppercase",
                            new KurzbezeichnungCreateDTO("a", "Test")),
                    arguments(
                            "kurzbez too long",
                            new KurzbezeichnungCreateDTO("ABCD", "Test")),
                    arguments(
                            "kurzbez too short",
                            new KurzbezeichnungCreateDTO("", "Test")),
                    arguments(
                            "bezeichnung too long",
                            new KurzbezeichnungCreateDTO("A", "a".repeat(201))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final KurzbezeichnungCreateDTO requestDTO) {

            restTestClient.post()
                    .uri("/kurzbezeichnungen")
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
            final KurzbezeichnungCreateDTO requestDTO = new KurzbezeichnungCreateDTO(NON_EXISTING_ID, "Test");

            restTestClient.post()
                    .uri("/kurzbezeichnungen")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class UpdateKurzbezeichnung {

        @Test
        void givenEntityExists_thenEntityIsUpdated() {
            final KurzbezeichnungUpdateDTO requestDTO = new KurzbezeichnungUpdateDTO("Test aktualisiert");

            final KurzbezeichnungResponseDTO responseDTO = restTestClient.put()
                    .uri("/kurzbezeichnungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(KurzbezeichnungResponseDTO.class)
                    .value(theEntityResponseDTO -> {
                        assertNotNull(theEntityResponseDTO);
                        assertThat(theEntityResponseDTO.id()).isEqualTo(EXISTING_ID);
                        assertThat(theEntityResponseDTO.bezeichnung()).isEqualTo(requestDTO.bezeichnung());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Kurzbezeichnung> entity = kurzbezeichnungRepository.findById(EXISTING_ID);
            assertThat(entity).isPresent();
            assertThat(entity.get().getBezeichnung()).isEqualTo(requestDTO.bezeichnung());
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            final KurzbezeichnungUpdateDTO requestDTO = new KurzbezeichnungUpdateDTO("Test aktualisiert");

            restTestClient.put()
                    .uri("/kurzbezeichnungen/{id}", NON_EXISTING_ID)
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
                            new KurzbezeichnungUpdateDTO("")),
                    arguments(
                            "bezeichnung too long",
                            new KurzbezeichnungUpdateDTO("a".repeat(201))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final KurzbezeichnungUpdateDTO requestDTO) {
            restTestClient.put()
                    .uri("/kurzbezeichnungen/{id}", EXISTING_ID)
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
            final KurzbezeichnungUpdateDTO requestDTO = new KurzbezeichnungUpdateDTO("Test aktualisiert");

            restTestClient.put()
                    .uri("/kurzbezeichnungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class DeleteKurzbezeichnung {

        @Test
        void givenEntityIdExists_thenEntityIsDeleted() {
            restTestClient.delete()
                    .uri("/kurzbezeichnungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk();

            assertThat(kurzbezeichnungRepository.findById(EXISTING_ID)).isEmpty();
        }

        @Test
        void givenEntityIdNotExists_thenReturnNotFound() {
            restTestClient.delete()
                    .uri("/kurzbezeichnungen/{id}", NON_EXISTING_ID)
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
                    .uri("/kurzbezeichnungen/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class GetKurzbezeichnungFormContext {

        @Test
        void givenNoEntitiesExist_thenReturnEmptyFormContext() {
            // Given
            kurzbezeichnungRepository.deleteAll();

            // When
            final KurzbezeichnungFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/kurzbezeichnungen/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(KurzbezeichnungFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.kurzbezeichnungen()).isEmpty();
        }

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // When
            final KurzbezeichnungFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/kurzbezeichnungen/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(KurzbezeichnungFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.kurzbezeichnungen()).hasSize(1);
            assertThat(result.kurzbezeichnungen().getFirst()).isEqualTo(EXISTING_ID);
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
                            .path("/kurzbezeichnungen/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

}
