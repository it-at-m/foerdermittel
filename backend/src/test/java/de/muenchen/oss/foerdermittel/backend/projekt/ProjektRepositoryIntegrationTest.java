package de.muenchen.oss.foerdermittel.backend.projekt;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
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
import de.muenchen.oss.foerdermittel.backend.projekt.dao.AuswertungProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@ActiveProfiles(profiles = { SPRING_TEST_PROFILE })
@Import(TestSecurityConfiguration.class)
class ProjektRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

    private static final String EXISTING_PROJNR = "3003000";

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
        exampleEntity.setStadtbezirk(stadtbezirk);
        exampleEntity.setUasUa(unterabschnitt);
        exampleEntity.setKurKurzbez(kurzbezeichnung);
        exampleEntity.setSiedlungsgebiet(siedlungsgebiet);
        exampleEntity.setBauprogramm(bauprogramm);

        exampleEntity.setKauf(false);
        exampleEntity.setRefinanzierbar(true);

        ReflectionTestUtils.setField(exampleEntity, "anlagevon", "test");
        ReflectionTestUtils.setField(exampleEntity, "anlagedatum", LocalDateTime.now());

        projektRepository.save(exampleEntity);
    }

    @Test
    void givenProjects_thenReturnsBasicProjektDAOs() {
        // When
        final List<BasicProjektDAO> result = projektRepository.findAllAsBasic();

        // Then
        assertThat(result)
                .contains(new BasicProjektDAO("3003000", "Projektname", "Projektstraße 1"));
    }

    @Test
    void givenProjects_thenReturnsAuswertungProjektDAOs() {
        // When
        final List<AuswertungProjektDAO> result = projektRepository.findAllAuswertungProjekte();

        // Then
        assertThat(result)
                .contains(new AuswertungProjektDAO("24", "Projektname", "Projektstraße 1", null));
    }
}
