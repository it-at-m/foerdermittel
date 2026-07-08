package de.muenchen.oss.foerdermittel.backend.benutzerhinweis;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestConstants;
import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisCreateDTO;
import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisResponseDTO;
import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisUpdateDTO;
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
class BenutzerhinweisIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestConstants.TESTCONTAINERS_POSTGRES_IMAGE));

    private static final String EXISTING_ID = "test";
    private static final String NON_EXISTING_ID = "test 2";

    @Autowired
    private BenutzerhinweisRepository benutzerhinweisRepository;

    @BeforeEach
    public void setUp() {
        benutzerhinweisRepository.deleteAll();
        final Benutzerhinweis exampleEntity = new Benutzerhinweis(EXISTING_ID, "Test", "Test", "Test");
        benutzerhinweisRepository.save(exampleEntity);
    }

    @Nested
    class GetBenutzerhinweis {

        @Test
        void givenEntityExists_thenReturnEntity() {
            restTestClient
                    .get()
                    .uri("/benutzerhinweise/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BenutzerhinweisResponseDTO.class)
                    .value(responseDTO -> {
                        assertNotNull(responseDTO);
                        assertThat(responseDTO.viewId()).isEqualTo(EXISTING_ID);
                    });
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            restTestClient
                    .get()
                    .uri("/benutzerhinweise/{id}", NON_EXISTING_ID)
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
                    .uri("/benutzerhinweise/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class CreateBenutzerhinweis {

        @Test
        void givenEntityNotExists_thenEntityIsSaved() {
            final BenutzerhinweisCreateDTO requestDTO = new BenutzerhinweisCreateDTO(NON_EXISTING_ID, "Test", "Test", "Test");

            final BenutzerhinweisResponseDTO responseDTO = restTestClient.post()
                    .uri("/benutzerhinweise")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BenutzerhinweisResponseDTO.class)
                    .value(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.funktionsbeschreibung()).isEqualTo(requestDTO.funktionsbeschreibung());
                        assertThat(response.bedienung()).isEqualTo(requestDTO.bedienung());
                        assertThat(response.pruefungVorgaben()).isEqualTo(requestDTO.pruefungVorgaben());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Benutzerhinweis> entity = benutzerhinweisRepository.findById(responseDTO.viewId());
            assertThat(entity).isPresent();
            assertThat(entity.get().getFunktionsbeschreibung()).isEqualTo(requestDTO.funktionsbeschreibung());
            assertThat(entity.get().getBedienung()).isEqualTo(requestDTO.bedienung());
            assertThat(entity.get().getPruefungVorgaben()).isEqualTo(requestDTO.pruefungVorgaben());
        }

        @Test
        void givenEntityAlreadyExists_thenReturnConflict() {
            final BenutzerhinweisCreateDTO requestDTO = new BenutzerhinweisCreateDTO(EXISTING_ID, "Test", "Test", "Test");

            restTestClient.post()
                    .uri("/benutzerhinweise")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.CONFLICT);
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "viewId too short",
                            new BenutzerhinweisCreateDTO("", "Test", "Test", "Test")),
                    arguments(
                            "viewId too long",
                            new BenutzerhinweisCreateDTO("a".repeat(31), "Test", "Test", "Test")),
                    arguments(
                            "funktionsbeschreibung too long",
                            new BenutzerhinweisCreateDTO("test", "a".repeat(4001), "test", "test")),
                    arguments(
                            "bedienung too long",
                            new BenutzerhinweisCreateDTO("test", "test", "a".repeat(4001), "test")),
                    arguments(
                            "pruefungVorgaben too long",
                            new BenutzerhinweisCreateDTO("test", "test", "test", "a".repeat(4001))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final BenutzerhinweisCreateDTO requestDTO) {

            restTestClient.post()
                    .uri("/benutzerhinweise")
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
            final BenutzerhinweisCreateDTO requestDTO = new BenutzerhinweisCreateDTO(NON_EXISTING_ID, "Test", "Test", "Test");

            restTestClient.post()
                    .uri("/benutzerhinweise")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class UpdateBenutzerhinweis {

        @Test
        void givenEntityExists_thenEntityIsUpdated() {
            final BenutzerhinweisUpdateDTO requestDTO = new BenutzerhinweisUpdateDTO("updated", "updated", "updated");

            final BenutzerhinweisResponseDTO responseDTO = restTestClient.put()
                    .uri("/benutzerhinweise/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(BenutzerhinweisResponseDTO.class)
                    .value(theEntityResponseDTO -> {
                        assertNotNull(theEntityResponseDTO);
                        assertThat(theEntityResponseDTO.viewId()).isEqualTo(EXISTING_ID);
                        assertThat(theEntityResponseDTO.funktionsbeschreibung()).isEqualTo(requestDTO.funktionsbeschreibung());
                        assertThat(theEntityResponseDTO.bedienung()).isEqualTo(requestDTO.bedienung());
                        assertThat(theEntityResponseDTO.pruefungVorgaben()).isEqualTo(requestDTO.pruefungVorgaben());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            final Optional<Benutzerhinweis> entity = benutzerhinweisRepository.findById(EXISTING_ID);
            assertThat(entity).isPresent();
            assertThat(entity.get().getFunktionsbeschreibung()).isEqualTo(requestDTO.funktionsbeschreibung());
            assertThat(entity.get().getBedienung()).isEqualTo(requestDTO.bedienung());
            assertThat(entity.get().getPruefungVorgaben()).isEqualTo(requestDTO.pruefungVorgaben());
        }

        @Test
        void givenEntityNotExists_thenReturnNotFound() {
            final BenutzerhinweisUpdateDTO requestDTO = new BenutzerhinweisUpdateDTO("updated", "updated", "updated");

            restTestClient.put()
                    .uri("/benutzerhinweise/{id}", NON_EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isNotFound();
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments(
                            "funktionsbeschreibung too long",
                            new BenutzerhinweisUpdateDTO("a".repeat(4001), "test", "test")),
                    arguments(
                            "bedienung too long",
                            new BenutzerhinweisUpdateDTO("test", "a".repeat(4001), "test")),
                    arguments(
                            "pruefungVorgaben too long",
                            new BenutzerhinweisUpdateDTO("test", "test", "a".repeat(4001))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final BenutzerhinweisUpdateDTO requestDTO) {
            restTestClient.put()
                    .uri("/benutzerhinweise/{id}", EXISTING_ID)
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
            final BenutzerhinweisUpdateDTO requestDTO = new BenutzerhinweisUpdateDTO("updated", "updated", "updated");

            restTestClient.put()
                    .uri("/benutzerhinweise/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

}
