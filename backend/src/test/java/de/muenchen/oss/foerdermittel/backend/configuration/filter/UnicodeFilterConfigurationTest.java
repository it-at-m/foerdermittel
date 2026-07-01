package de.muenchen.oss.foerdermittel.backend.configuration.filter;

import static de.muenchen.oss.foerdermittel.backend.TestConstants.SPRING_TEST_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.muenchen.oss.foerdermittel.backend.FoerdermittelBackendApplication;
import de.muenchen.oss.foerdermittel.backend.TestConstants;
import de.muenchen.oss.foerdermittel.backend.TestSecurityConfiguration;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammRepository;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammResponseDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(
        classes = { FoerdermittelBackendApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureRestTestClient
@ActiveProfiles(profiles = { SPRING_TEST_PROFILE })
@Import(TestSecurityConfiguration.class)
class UnicodeFilterConfigurationTest {

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestConstants.TESTCONTAINERS_POSTGRES_IMAGE));

    private static final String ENTITY_ENDPOINT_URL = "/bauprogramme";

    /**
     * Decomposed string:
     * String "Ä-é" represented with unicode letters "A◌̈-e◌́"
     */
    private static final String TEXT_ATTRIBUTE_DECOMPOSED = "\u0041\u0308-\u0065\u0301";

    /**
     * Composed string:
     * String "Ä-é" represented with unicode letters "Ä-é".
     */
    private static final String TEXT_ATTRIBUTE_COMPOSED = "\u00c4-\u00e9";

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private BauprogrammRepository bauprogrammRepository;

    @Test
    void givenDecomposedString_thenConvertToNfcNormalized() {
        // Given
        // Persist entity with decomposed string.
        final BauprogrammCreateDTO requestDTO = new BauprogrammCreateDTO(1, TEXT_ATTRIBUTE_DECOMPOSED);

        // When
        final BauprogrammResponseDTO responseDTO = restTestClient.post()
                .uri(ENTITY_ENDPOINT_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer admin")
                .body(requestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BauprogrammResponseDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseDTO);

        final Bauprogramm entity = bauprogrammRepository.findById(BigDecimal.valueOf(Integer.parseInt(responseDTO.id()))).orElse(null);

        // Then
        // Check whether response contains a composed string.
        assertNotNull(responseDTO.bezeichnung());
        assertEquals(TEXT_ATTRIBUTE_COMPOSED, responseDTO.bezeichnung());
        assertEquals(TEXT_ATTRIBUTE_COMPOSED.length(), responseDTO.bezeichnung().length());

        // Check persisted entity contains a composed string via JPA repository.
        assertNotNull(entity);
        assertNotNull(entity.getBezeichnung());
        assertEquals(TEXT_ATTRIBUTE_COMPOSED, entity.getBezeichnung());
        assertEquals(TEXT_ATTRIBUTE_COMPOSED.length(), entity.getBezeichnung().length());
    }

}
