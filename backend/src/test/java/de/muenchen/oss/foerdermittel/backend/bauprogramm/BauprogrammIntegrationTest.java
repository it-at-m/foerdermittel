package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestConstants;
import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammResponseDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammUpdateDTO;
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
class BauprogrammIntegrationTest {

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
    private BauprogrammRepository bauprogrammRepository;

    @BeforeEach
    public void setUp() {
        bauprogrammRepository.deleteAll();
        final Bauprogramm exampleEntity = new Bauprogramm(new BigDecimal(EXISTING_ID), "Test");
        bauprogrammRepository.save(exampleEntity);
    }

    @Nested
    class GetBauprogramm {

        @Test
        void givenEntityExists_thenReturnEntity() {
            restTestClient
                    .get()
                    .uri("/bauprogramme/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BauprogrammResponseDTO.class)
                    .value(responseDTO -> {
                        assertNotNull(responseDTO);
                        assertThat(responseDTO.id()).isEqualTo(String.valueOf(EXISTING_ID));
                    });
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            restTestClient
                    .get()
                    .uri("/bauprogramme/{id}", NON_EXISTING_ID)
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
                    .uri("/bauprogramme/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class GetBauprogramme {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/bauprogramme")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.content")
                    .value(new ParameterizedTypeReference<List<BauprogrammResponseDTO>>() {
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
                            .path("/bauprogramme")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class CreateBauprogramm {

        @Test
        void givenEntityNotExists_thenEntityIsSaved() {
            final BauprogrammCreateDTO requestDTO = new BauprogrammCreateDTO(NON_EXISTING_ID, "Test");

            final BauprogrammResponseDTO responseDTO = restTestClient.post()
                    .uri("/bauprogramme")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BauprogrammResponseDTO.class)
                    .value(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.bauprogramm()).isEqualTo(requestDTO.bauprogramm());
                        assertThat(response.bezeichnung()).isEqualTo(requestDTO.bezeichnung());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Bauprogramm> entity = bauprogrammRepository.findById(BigDecimal.valueOf(Integer.parseInt(responseDTO.id())));
            assertThat(entity).isPresent();
            assertThat(entity.get().getBauprogramm().intValue()).isEqualTo(requestDTO.bauprogramm());
            assertThat(entity.get().getBezeichnung()).isEqualTo(requestDTO.bezeichnung());
        }

        @Test
        void givenEntityAlreadyExists_thenReturnConflict() {
            final BauprogrammCreateDTO requestDTO = new BauprogrammCreateDTO(EXISTING_ID, "Test");

            restTestClient.post()
                    .uri("/bauprogramme")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.CONFLICT);
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "bauprogramm too high",
                            new BauprogrammCreateDTO(100, "Test")),
                    arguments(
                            "bezeichnung too short",
                            new BauprogrammCreateDTO(2, "")),
                    arguments(
                            "bezeichnung too long",
                            new BauprogrammCreateDTO(2, "a".repeat(201))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final BauprogrammCreateDTO requestDTO) {

            restTestClient.post()
                    .uri("/bauprogramme")
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
            final BauprogrammCreateDTO requestDTO = new BauprogrammCreateDTO(NON_EXISTING_ID, "Test");

            restTestClient.post()
                    .uri("/bauprogramme")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class UpdateBauprogramm {

        @Test
        void givenEntityExists_thenEntityIsUpdated() {
            final BauprogrammUpdateDTO requestDTO = new BauprogrammUpdateDTO("Test aktualisiert");

            final BauprogrammResponseDTO responseDTO = restTestClient.put()
                    .uri("/bauprogramme/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BauprogrammResponseDTO.class)
                    .value(theEntityResponseDTO -> {
                        assertNotNull(theEntityResponseDTO);
                        assertThat(theEntityResponseDTO.id()).isEqualTo(String.valueOf(EXISTING_ID));
                        assertThat(theEntityResponseDTO.bezeichnung()).isEqualTo(requestDTO.bezeichnung());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Bauprogramm> entity = bauprogrammRepository.findById(BigDecimal.valueOf(EXISTING_ID));
            assertThat(entity).isPresent();
            assertThat(entity.get().getBezeichnung()).isEqualTo(requestDTO.bezeichnung());
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            final BauprogrammUpdateDTO requestDTO = new BauprogrammUpdateDTO("Test aktualisiert");

            restTestClient.put()
                    .uri("/bauprogramme/{id}", NON_EXISTING_ID)
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
                            new BauprogrammUpdateDTO("")),
                    arguments(
                            "bezeichnung too long",
                            new BauprogrammUpdateDTO("a".repeat(201))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final BauprogrammUpdateDTO requestDTO) {
            restTestClient.put()
                    .uri("/bauprogramme/{id}", EXISTING_ID)
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
            final BauprogrammUpdateDTO requestDTO = new BauprogrammUpdateDTO("Test aktualisiert");

            restTestClient.put()
                    .uri("/bauprogramme/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class DeleteBauprogramm {

        @Test
        void givenEntityIdExists_thenEntityIsDeleted() {
            restTestClient.delete()
                    .uri("/bauprogramme/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk();

            assertThat(bauprogrammRepository.findById(BigDecimal.valueOf(EXISTING_ID))).isEmpty();
        }

        @Test
        void givenEntityIdNotExists_thenReturnNotFound() {
            restTestClient.delete()
                    .uri("/bauprogramme/{id}", NON_EXISTING_ID)
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
                    .uri("/bauprogramme/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class GetBauprogrammFormContext {

        @Test
        void givenNoEntitiesExist_thenReturnEmptyFormContext() {
            // Given
            bauprogrammRepository.deleteAll();

            // When
            final BauprogrammFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/bauprogramme/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BauprogrammFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.bauprogramme()).isEmpty();
        }

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // When
            final BauprogrammFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/bauprogramme/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BauprogrammFormContext.class)
                    .returnResult()
                    .getResponseBody();

            // Then
            assertThat(result).isNotNull();
            assertThat(result.bauprogramme()).hasSize(1);
            assertThat(result.bauprogramme().getFirst()).isEqualByComparingTo(String.valueOf(EXISTING_ID));
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
                            .path("/bauprogramme/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

}
