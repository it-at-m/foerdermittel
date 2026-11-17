package de.muenchen.oss.foerdermittel.backend.projekt;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammRepository;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichRepository;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.HauptabschnittRepository;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.Siedlungsgebiet;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietRepository;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkRepository;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DataJpaTest
@ActiveProfiles(profiles = { SPRING_TEST_PROFILE })
class ProjektRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestUtils.getImageFromDockerCompose("postgres")));

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

    @Test
    void givenProjects_thenReturnsTheirBasicProjection() {
        // Given
        final Foerderbereich foerderbereich = new Foerderbereich(
                BigDecimal.valueOf(99), "Test", false, false, false, false);
        foerderbereichRepository.save(foerderbereich);

        final Stadtbezirk stadtbezirk = new Stadtbezirk(
                BigDecimal.valueOf(99), "Test");
        stadtbezirkRepository.save(stadtbezirk);

        final Hauptabschnitt exampleHa2 = new Hauptabschnitt("ha", "bz2");
        hauptabschnittRepository.save(exampleHa2);
        final Unterabschnitt unterabschnitt = new Unterabschnitt("L", "Test", exampleHa2);
        unterabschnittRepository.save(unterabschnitt);

        final Kurzbezeichnung kurzbezeichnung = new Kurzbezeichnung("KB1", "Test");
        kurzbezeichnungRepository.save(kurzbezeichnung);

        final Siedlungsgebiet siedlungsgebiet = new Siedlungsgebiet(new BigDecimal(1), "Test");
        siedlungsgebietRepository.save(siedlungsgebiet);

        final Bauprogramm bauprogramm = new Bauprogramm(new BigDecimal(1), "Test");
        bauprogrammRepository.save(bauprogramm);

        projektRepository.save(new Projekt(
                "1234567",
                "2023",
                foerderbereich,
                "1",
                "1",
                "Test",
                "Test 1",
                stadtbezirk,
                unterabschnitt,
                kurzbezeichnung,
                Krisofp.K));

        // When
        final List<BasicProjektDAO> result = projektRepository.findAllAsBasic();

        // Then
        assertThat(result).contains(new BasicProjektDAO("1234567", "Test", "Test 1"));
    }
}
