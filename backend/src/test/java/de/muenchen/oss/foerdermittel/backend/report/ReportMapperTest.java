package de.muenchen.oss.foerdermittel.backend.report;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.dto.ReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportProjektuebersichtDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichworteDTO;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportMapperTest {

    private final ReportMapper reportMapper = new ReportMapper();

    @Test
    void givenReportStichworteDTO_thenReturnsCorrectParameters() {
        // given
        final ReportStichworteDTO dto = new ReportStichworteDTO("TEST");

        // when
        Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters).hasSize(1).containsEntry("P_BEREICH", dto.bereich());
    }

    @Test
    void givenReportProjektuebersichtDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportProjektuebersichtDTO dto = new ReportProjektuebersichtDTO("P-123", true);

        // When
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

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
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters).containsEntry("P_NOTIZ", "0");
    }

}
