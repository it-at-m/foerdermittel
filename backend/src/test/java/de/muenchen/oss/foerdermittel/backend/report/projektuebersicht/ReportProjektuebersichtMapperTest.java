package de.muenchen.oss.foerdermittel.backend.report.projektuebersicht;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.projektuebersicht.dto.ReportProjektuebersichtDTO;
import de.muenchen.oss.foerdermittel.backend.report.projektuebersicht.dto.ReportProjektuebersichtMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportProjektuebersichtMapperTest {

    private final ReportProjektuebersichtMapper reportProjektuebersichtMapper = new ReportProjektuebersichtMapper();

    @Test
    void givenReportProjektuebersichtDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportProjektuebersichtDTO dto = new ReportProjektuebersichtDTO("P-123", true);

        // When
        final Map<String, Object> parameters = reportProjektuebersichtMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters)
                .hasSize(2)
                .containsEntry("P_PROJNR", dto.projnr())
                .containsEntry("P_NOTIZ", "1");
    }

    @Test
    void givenReportProjektuebersichtDTOWithoutNotiz_thenMapsFalseToZero() {
        // Given
        final ReportProjektuebersichtDTO dto = new ReportProjektuebersichtDTO("P-123", false);

        // When
        final Map<String, Object> parameters = reportProjektuebersichtMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters).containsEntry("P_NOTIZ", "0");
    }

}
