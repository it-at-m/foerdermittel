package de.muenchen.oss.foerdermittel.backend.report;

import static de.muenchen.oss.foerdermittel.backend.report.dto.ReportAuwertungProjektSort.PROJEKTNUMMER;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportAuswertungProjekteDTO;
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

    @Test
    void givenReportAuswertungProjekteDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportAuswertungProjekteDTO dto = new ReportAuswertungProjekteDTO("2025", "1","1","11","KB1","Projektstraße 1", "Projekt 1","1","SBL1",
                "1", Krisofp.K,"0","0","0",PROJEKTNUMMER,ReportFormat.PDF );

        // When
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters)
                .hasSize(14)
                .containsEntry("P_JAHR", dto.jahr())
                .containsEntry("P_BEZ", "1")
                .containsEntry("P_FB", "1")
                .containsEntry("P_UA", "11")
                .containsEntry("P_KURZ", "KB1")
                .containsEntry("P_PSTRASSE", "%Projektstraße 1%")
                .containsEntry("P_PNAME", "%Projekt 1%")
                .containsEntry("P_SGT", "1")
                .containsEntry("P_SBL", "SBL1")
                .containsEntry("P_BPG", "1")
                .containsEntry("P_KAUF", "0")
                .containsEntry("P_OFFEN", "0")
                .containsEntry("P_KRISOFP", Krisofp.K.name())
                .containsEntry("P_RELEVANT", "0");
    }

}
