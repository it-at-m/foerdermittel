package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import de.muenchen.oss.foerdermittel.backend.TestConstants;
import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammResponseDTO;

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
import org.junit.jupiter.params.provider.ValueSource;
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

        @ParameterizedTest(name = "role ''{0}'' is allowed")
        @ValueSource(strings = {
                "sachbearbeitung",
                "sachbearbeitunghaushalt",
                "admin"
        })
        void givenAllowedRole_thenReturnOk(String role) {
            restTestClient
                    .get()
                    .uri("/bauprogramme/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isOk();
        }

    }

    @Nested
    class GetBauprogramme {

        @Test
        void givenValidPageNumber_thenReturnPageOfEntities() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/bauprogramme")
                            .queryParam("pageNumber", "0")
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

        @Test
        void givenInvalidPageNumber_thenReturnEmptyPage() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/bauprogramme")
                            .queryParam("pageNumber", "99")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.content")
                    .value(new ParameterizedTypeReference<List<BauprogrammResponseDTO>>() {
                    }, content -> assertThat(content).isEmpty());
        }

        @ParameterizedTest(name = "role ''{0}'' is allowed")
        @ValueSource(strings = {
                "sachbearbeitung",
                "sachbearbeitunghaushalt",
                "admin"
        })
        void givenAllowedRole_thenReturnOk(String role) {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/bauprogramme")
                            .queryParam("pageNumber", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isOk();
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
            final Optional<Bauprogramm> entity = bauprogrammRepository.findById(Integer.valueOf(responseDTO.id()));
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
                            new BauprogrammCreateDTO(100, "Test")
                    ),
                    arguments(
                            "bezeichnung too long",
                            new BauprogrammCreateDTO(2, "a".repeat(201))
                    )
            );
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                String description,
                BauprogrammCreateDTO requestDTO) {

            restTestClient.post()
                    .uri("/bauprogramme")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isBadRequest();
        }

        @ParameterizedTest(name = "role ''{0}'' is allowed")
        @ValueSource(strings = {
                "admin"
        })
        void givenAllowedRole_thenReturnCreated(String role) {
            final BauprogrammCreateDTO requestDTO = new BauprogrammCreateDTO(NON_EXISTING_ID, "Test");

            restTestClient.post()
                    .uri("/bauprogramme")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated();
        }

        @ParameterizedTest(name = "role ''{0}'' is forbidden")
        @ValueSource(strings = {
                "sachbearbeitung",
                "sachbearbeitunghaushalt"
        })
        void givenDisallowedRole_thenReturnForbidden(String role) {
            final BauprogrammCreateDTO requestDTO = new BauprogrammCreateDTO(5, "Test");

            restTestClient.post()
                    .uri("/bauprogramme")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isForbidden();
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
            final Optional<Bauprogramm> entity = bauprogrammRepository.findById(EXISTING_ID);
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
                            "bauprogramm too high",
                            new BauprogrammUpdateDTO("a".repeat(201))
                    )
            );
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                String description,
                BauprogrammUpdateDTO requestDTO) {
            restTestClient.put()
                    .uri("/bauprogramme/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isBadRequest();
        }

        @ParameterizedTest(name = "role ''{0}'' is allowed")
        @ValueSource(strings = {
                "admin"
        })
        void givenAllowedRole_thenReturnOk(String role) {
            final BauprogrammUpdateDTO requestDTO = new BauprogrammUpdateDTO("Test aktualisiert");

            restTestClient.put()
                    .uri("/bauprogramme/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk();
        }

        @ParameterizedTest(name = "role ''{0}'' is forbidden")
        @ValueSource(strings = {
                "sachbearbeitung",
                "sachbearbeitunghaushalt"
        })
        void givenDisallowedRole_thenReturnForbidden(String role) {
            final BauprogrammUpdateDTO requestDTO = new BauprogrammUpdateDTO("Test aktualisiert");

            restTestClient.put()
                    .uri("/bauprogramme/{id}", EXISTING_ID)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isForbidden();
        }

    }

//    @Nested
//    class DeleteEntity {
//        @Test
//        void givenEntityId_thenEntityIsDeleted() {
//            restTestClient.delete()
//                    .uri("/theEntity/{theEntityID}", testEntityId)
//                    .header(HttpHeaders.AUTHORIZATION, "Bearer writer")
//                    .exchange()
//                    .expectStatus().isOk();
//
//            assertThat(theEntityRepository.findById(testEntityId)).isEmpty();
//        }
//    }

}