package de.muenchen.oss.foerdermittel.backend.projekt;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ProjektMapperTest {

    private final ProjektMapper projektMapper = Mappers.getMapper(ProjektMapper.class);

    @Test
    void givenBasicProjektDAOs_thenMapsTheirReportFormContexts() {
        // Given
        final List<BasicProjektDAO> projekte = List.of(
                new BasicProjektDAO("P-123", "Projektname", "Projektstraße 1"));

        // When
        final List<ReportProjektuebersichtFormContextDTO> result = projektMapper.toReportFormContext(projekte);

        // Then
        assertThat(result).containsExactly(
                new ReportProjektuebersichtFormContextDTO("P-123", "Projektname", "Projektstraße 1"));
    }
}
