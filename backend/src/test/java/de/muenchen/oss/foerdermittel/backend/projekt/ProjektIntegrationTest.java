package de.muenchen.oss.foerdermittel.backend.projekt;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammRepository;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichRepository;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.HauptabschnittRepository;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektResponseDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.Siedlungsgebiet;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietRepository;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkRepository;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
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
class ProjektIntegrationTest {

    @Autowired
    private RestTestClient restTestClient;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

    private static final String EXISTING_PROJNR = "3003000";
    private static final String NON_PROJNR = "3003001";

    @MockitoBean
    private AuditorAware<String> auditorAware;

    @Autowired
    private ProjektRepository projektRepository;

    @Autowired
    private FoerderbereichRepository foerderbereichRepository;

    @Autowired
    private StadtbezirkRepository stadtbezirkRepository;

    @Autowired
    private UnterabschnittRepository unterabschnittRepository;

    @Autowired
    private HauptabschnittRepository hauptabschnittRepository;

    @Autowired
    private KurzbezeichnungRepository kurzbezeichnungRepository;

    @Autowired
    private SiedlungsgebietRepository siedlungsgebietRepository;

    @Autowired
    private BauprogrammRepository bauprogrammRepository;

    @BeforeEach
    public void setUp() {
        when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of("test"));

        projektRepository.deleteAll();

        final Foerderbereich foerderbereich = new Foerderbereich(BigDecimal.valueOf(99), "Test", false, false, false, false);
        foerderbereichRepository.save(foerderbereich);

        final Stadtbezirk stadtbezirk = new Stadtbezirk(BigDecimal.valueOf(99), "Test");
        stadtbezirkRepository.save(stadtbezirk);

        final Hauptabschnitt exampleHa2 = new Hauptabschnitt("ha", "bz2");
        hauptabschnittRepository.save(exampleHa2);

        final Unterabschnitt unterabschnitt = new Unterabschnitt("L", "Test", exampleHa2);
        unterabschnittRepository.save(unterabschnitt);

        final Kurzbezeichnung kurzbezeichnung = new Kurzbezeichnung("KB1", "Test");
        kurzbezeichnungRepository.save(kurzbezeichnung);

        final Siedlungsgebiet siedlungsgebiet = new Siedlungsgebiet(BigDecimal.ONE, "Test");
        siedlungsgebietRepository.save(siedlungsgebiet);

        final Bauprogramm bauprogramm = new Bauprogramm(BigDecimal.ONE, "Test");
        bauprogrammRepository.save(bauprogramm);

        final Projekt exampleEntity = new Projekt();
        exampleEntity.setProjnr(EXISTING_PROJNR);
        exampleEntity.setJahr("24");
        exampleEntity.setLfdnr1("0");
        exampleEntity.setLfdnr2("01");
        exampleEntity.setPname("Projektname");
        exampleEntity.setPstrasse("Projektstraße 1");

        exampleEntity.setFobFb(foerderbereich);
        exampleEntity.setBezStadtbezirk(stadtbezirk);
        exampleEntity.setUasUa(unterabschnitt);
        exampleEntity.setKurKurzbez(kurzbezeichnung);
        exampleEntity.setSgtSiedlungsgebiet(siedlungsgebiet);
        exampleEntity.setBpgBauprogramm(bauprogramm);

        exampleEntity.setKauf(false);
        exampleEntity.setRefinanzierbar(true);

        ReflectionTestUtils.setField(exampleEntity, "anlagevon", "test");
        ReflectionTestUtils.setField(exampleEntity, "anlagedatum", LocalDateTime.now());

        projektRepository.save(exampleEntity);
    }

    @Nested
    class GetProjekte {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/projekte")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer sachbearbeitung")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.content")
                    .value(new ParameterizedTypeReference<List<ProjektResponseDTO>>() {
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
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/projekte")
                            .queryParam("page", "0")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class GetProjektFormContext {

        @Test
        void givenNoEntitiesExist_thenReturnEmptyFormContext() {
            projektRepository.deleteAll();

            final ProjektFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/projekte/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProjektFormContext.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(result).isNotNull();
            assertThat(result.projekte()).isEmpty();
        }

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            final ProjektFormContext result = restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/projekte/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProjektFormContext.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(result).isNotNull();
            assertThat(result.projekte()).hasSize(1);
            assertThat(result.projekte().getFirst().projnr()).isEqualTo(EXISTING_PROJNR);
            ;
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
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/projekte/form-context")
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

    @Nested
    class CreateProjekt {
        private static ProjektCreateDTO createProjektDTO(final String projnr) {
            return new ProjektCreateDTO(projnr, 99, 99, null, "KB1", "L", 1, 26, 0, 1, "Projektname", "Projektstraße 1", "Test", true, false, null, null, null,
                    "Testnotiz", 1, null, null);
        }

        @Test
        void givenEntityNotExists_thenEntityIsSaved() {
            final ProjektCreateDTO requestDTO = createProjektDTO(NON_PROJNR);

            final ProjektResponseDTO responseDTO = restTestClient.post()
                    .uri("/projekte")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProjektResponseDTO.class)
                    .value(response -> {
                        assertThat(response).isNotNull();
                        assertThat(response.projnr()).isEqualTo(requestDTO.projnr());
                        assertThat(response.jahr()).isEqualTo(requestDTO.jahr());
                        assertThat(response.pname()).isEqualTo(requestDTO.pname());
                        assertThat(response.pstrasse()).isEqualTo(requestDTO.pstrasse());
                        assertThat(response.projart()).isEqualTo(requestDTO.projart());
                        assertThat(response.refinanzierbar()).isEqualTo(requestDTO.refinanzierbar());
                        assertThat(response.kauf()).isEqualTo(requestDTO.kauf());
                        assertThat(response.notizen()).isEqualTo(requestDTO.notizen());
                        assertThat(response.fobFb()).isEqualByComparingTo(BigDecimal.valueOf(requestDTO.fobFb()));
                        assertThat(response.bezStadtbezirk()).isEqualByComparingTo(BigDecimal.valueOf(requestDTO.bezStadtbezirk()));
                        assertThat(response.uasUa()).isEqualTo(requestDTO.uasUa());
                        assertThat(response.kurKurzbez()).isEqualTo(requestDTO.kurKurzbez());
                        assertThat(response.sgtSiedlungsgebiet()).isEqualByComparingTo(BigDecimal.valueOf(requestDTO.sgtSiedlungsgebiet()));
                        assertThat(response.bpgBauprogramm()).isEqualByComparingTo(BigDecimal.valueOf(requestDTO.bpgBauprogramm()));
                        assertThat(response.krnKrhname()).isEqualTo(requestDTO.krnKrhname());
                        assertThat(response.krisofp()).isEqualTo(requestDTO.krisofp());
                        assertThat(response.krhzweck()).isEqualTo(requestDTO.krhzweck());
                    })
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();

            final Projekt entity = projektRepository.findById(responseDTO.id()).orElseThrow();

            assertThat(entity.getProjnr()).isEqualTo(requestDTO.projnr());
            assertThat(entity.getJahr()).isEqualTo(String.valueOf(requestDTO.jahr()));
            assertThat(entity.getLfdnr1()).isEqualTo(String.valueOf(requestDTO.lfdnr1()));
            assertThat(entity.getLfdnr2()).isEqualTo(String.valueOf(requestDTO.lfdnr2()));
            assertThat(entity.getPname()).isEqualTo(requestDTO.pname());
            assertThat(entity.getPstrasse()).isEqualTo(requestDTO.pstrasse());
            assertThat(entity.getProjart()).isEqualTo(requestDTO.projart());
            assertThat(entity.getRefinanzierbar()).isEqualTo(requestDTO.refinanzierbar());
            assertThat(entity.getKauf()).isEqualTo(requestDTO.kauf());
            assertThat(entity.getNotizen()).isEqualTo(requestDTO.notizen());

            assertThat(entity.getFobFb().getFb()).isEqualByComparingTo(BigDecimal.valueOf(requestDTO.fobFb()));
            assertThat(entity.getBezStadtbezirk().getStadtbezirk()).isEqualByComparingTo(BigDecimal.valueOf(requestDTO.bezStadtbezirk()));
            assertThat(entity.getUasUa().getUa()).isEqualTo(requestDTO.uasUa());
            assertThat(entity.getKurKurzbez().getKurzbez()).isEqualTo(requestDTO.kurKurzbez());
            assertThat(entity.getSgtSiedlungsgebiet().getSiedlungsgebiet()).isEqualByComparingTo(BigDecimal.valueOf(requestDTO.sgtSiedlungsgebiet()));
            assertThat(entity.getBpgBauprogramm().getBauprogramm()).isEqualByComparingTo(BigDecimal.valueOf(requestDTO.bpgBauprogramm()));

            assertThat(entity.getAnlagevon()).isEqualTo("test");
            assertThat(entity.getAnlagedatum()).isNotNull();
            assertThat(entity.getAnlagedatum()).isBeforeOrEqualTo(LocalDateTime.now());
        }

        @Test
        void givenEntityAlreadyExists_thenReturnConflict() {
            restTestClient.post()
                    .uri("/projekte")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(createProjektDTO(EXISTING_PROJNR))
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isEqualTo(HttpStatus.CONFLICT);
        }

        private static Stream<Arguments> invalidInputRequests() {
            return Stream.of(
                    arguments("kurzbez not uppercase",
                            createProjektDTO(NON_PROJNR, "kb1")),
                    arguments("kurzbez too long",
                            createProjektDTO(NON_PROJNR, "ABCD")),
                    arguments("kurzbez too short",
                            createProjektDTO(NON_PROJNR, "")),
                    arguments("pname too long",
                            createProjektDTO(NON_PROJNR, "ABC", "a".repeat(101))));
        }

        private static ProjektCreateDTO createProjektDTO(
                final String projnr,
                final String kurzbez) {
            return createProjektDTO(projnr, kurzbez, "Projektname");
        }

        private static ProjektCreateDTO createProjektDTO(
                final String projnr,
                final String kurzbez,
                final String pname) {
            return new ProjektCreateDTO(
                    projnr, 99, 99, null, kurzbez, "L", 1,
                    26, 0, 1, pname, "Projektstraße 1", "Test",
                    true, false, null, null, null, "Testnotiz",
                    1, null, null);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidInputRequests")
        void givenInvalidInput_thenReturnBadRequest(
                final String description,
                final ProjektCreateDTO requestDTO) {
            restTestClient.post()
                    .uri("/projekte")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }

    @Nested
    class UpdateProjekt {

        private static ProjektUpdateDTO createValidProjektUpdateDTO() {
            return new ProjektUpdateDTO(
                    "Neuer Projektname", "Neue Projektstraße 1", 99, 99, null, "KB1", 1, "Test", true,
                    false, null, null, null, "Aktualisierte Notiz", 1, null, null);
        }

        @Test
        void givenValidUpdateDTOAndExistingProjekt_thenProjektIsUpdated() {
            final ProjektUpdateDTO requestDTO = createValidProjektUpdateDTO();

            final ProjektResponseDTO responseDTO = restTestClient.put()
                    .uri("/projekte/{id}", EXISTING_PROJNR)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .body(requestDTO)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProjektResponseDTO.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(responseDTO).isNotNull();
            assertThat(responseDTO.projnr()).isEqualTo(EXISTING_PROJNR);
            assertThat(responseDTO.pname()).isEqualTo(requestDTO.pname());
            assertThat(responseDTO.pstrasse()).isEqualTo(requestDTO.pstrasse());
            assertThat(responseDTO.projart()).isEqualTo(requestDTO.projart());
            assertThat(responseDTO.refinanzierbar()).isEqualTo(requestDTO.refinanzierbar());
            assertThat(responseDTO.kauf()).isEqualTo(requestDTO.kauf());
            assertThat(responseDTO.notizen()).isEqualTo(requestDTO.notizen());
            assertThat(responseDTO.fobFb()).isEqualByComparingTo(BigDecimal.valueOf(requestDTO.fobFb()));
            assertThat(responseDTO.bezStadtbezirk())
                    .isEqualByComparingTo(BigDecimal.valueOf(requestDTO.bezStadtbezirk()));
            assertThat(responseDTO.kurKurzbez()).isEqualTo(requestDTO.kurKurzbez());
            assertThat(responseDTO.sgtSiedlungsgebiet())
                    .isEqualByComparingTo(BigDecimal.valueOf(requestDTO.sgtSiedlungsgebiet()));
            assertThat(responseDTO.bpgBauprogramm())
                    .isEqualByComparingTo(BigDecimal.valueOf(requestDTO.bpgBauprogramm()));

            final Projekt entity = projektRepository.findById(EXISTING_PROJNR).orElseThrow();

            assertThat(entity.getPname()).isEqualTo(requestDTO.pname());
            assertThat(entity.getPstrasse()).isEqualTo(requestDTO.pstrasse());
            assertThat(entity.getProjart()).isEqualTo(requestDTO.projart());
            assertThat(entity.getRefinanzierbar()).isEqualTo(requestDTO.refinanzierbar());
            assertThat(entity.getKauf()).isEqualTo(requestDTO.kauf());
            assertThat(entity.getNotizen()).isEqualTo(requestDTO.notizen());
            assertThat(entity.getFobFb().getFb())
                    .isEqualByComparingTo(BigDecimal.valueOf(requestDTO.fobFb()));
            assertThat(entity.getBezStadtbezirk().getStadtbezirk())
                    .isEqualByComparingTo(BigDecimal.valueOf(requestDTO.bezStadtbezirk()));
            assertThat(entity.getUasUa().getUa()).isEqualTo("L");
            assertThat(entity.getKurKurzbez().getKurzbez()).isEqualTo(requestDTO.kurKurzbez());
            assertThat(entity.getSgtSiedlungsgebiet().getSiedlungsgebiet())
                    .isEqualByComparingTo(BigDecimal.valueOf(requestDTO.sgtSiedlungsgebiet()));
            assertThat(entity.getBpgBauprogramm().getBauprogramm())
                    .isEqualByComparingTo(BigDecimal.valueOf(requestDTO.bpgBauprogramm()));
            assertThat(entity.getProjnr()).isEqualTo(EXISTING_PROJNR);
            assertThat(entity.getJahr()).isEqualTo("24");
            assertThat(entity.getLfdnr1()).isEqualTo("0");
            assertThat(entity.getLfdnr2()).isEqualTo("01");
        }
    }

    @Nested
    class DeleteProjekt {

        @Test
        void givenEntityIdExists_thenEntityIsDeleted() {
            restTestClient.delete()
                    .uri("/projekte/{id}", EXISTING_PROJNR)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isOk();

            assertThat(projektRepository.findById(EXISTING_PROJNR)).isEmpty();
        }

        @Test
        void givenEntityIdNotExists_thenReturnNotFound() {
            restTestClient.delete()
                    .uri("/projekte/{id}", NON_PROJNR)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                    .exchange()
                    .expectStatus().isNotFound();
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
        void givenRole_thenReturnStatus(final String role, final HttpStatus httpStatus) {
            restTestClient.delete()
                    .uri("/projekte/{id}", EXISTING_PROJNR)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", role))
                    .exchange()
                    .expectStatus().isEqualTo(httpStatus);
        }

    }

}
