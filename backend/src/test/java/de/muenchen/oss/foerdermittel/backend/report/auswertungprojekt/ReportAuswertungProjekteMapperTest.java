package de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportAuswertungProjekteDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static de.muenchen.oss.foerdermittel.backend.report.dto.ReportAuwertungProjektSort.PROJEKTNUMMER;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportAuswertungProjekteMapperTest {
    private final ReportMapper reportMapper = new ReportMapper();

    @Test
    void givenReportAuswertungProjekteDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportAuswertungProjekteDTO dto = new ReportAuswertungProjekteDTO("2025", "1","1","11","KB1","Projektstraße 1", "Projekt 1","1","SBL1",
                "1", Krisofp.K,"0","0","0",PROJEKTNUMMER, ReportFormat.PDF );

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