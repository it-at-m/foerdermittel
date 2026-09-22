package de.muenchen.oss.foerdermittel.backend.projekt;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.TestUtils;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DataJpaTest
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

    @Test
    void givenProjects_thenReturnsTheirBasicProjection() {
        // Given
        final Foerderbereich foerderbereich = new Foerderbereich(
                BigDecimal.valueOf(99), "Test", false, false, false, false);
        foerderbereichRepository.save(foerderbereich);
        projektRepository.save(new Projekt("P-123", "Projektname", "Projektstraße 1", foerderbereich));

        // When
        final List<BasicProjektDAO> result = projektRepository.findAllAsBasic();

        // Then
        assertThat(result).contains(new BasicProjektDAO("P-123", "Projektname", "Projektstraße 1"));
    }
}
