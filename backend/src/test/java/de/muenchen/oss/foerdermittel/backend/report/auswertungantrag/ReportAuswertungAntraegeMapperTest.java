package de.muenchen.oss.foerdermittel.backend.report.auswertungantrag;

import static de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto.ReportAuswertungAntraegeSort.PROJEKTNUMMER;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.projekt.Antrag;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto.ReportAuswertungAntraegeDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto.ReportAuswertungAntraegeMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportAuswertungAntraegeMapperTest {
    private final ReportAuswertungAntraegeMapper reportAuswertungAntraegeMapper = new ReportAuswertungAntraegeMapper();

    @Test
    void givenReportAuswertungAntraegeDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportAuswertungAntraegeDTO dto = new ReportAuswertungAntraegeDTO("2023", "2025", "1", "1", "11", "KB1", "Projektstraße 1", "Projekt 1",
                "1234567", "1",
                "SBL1", "1", Krisofp.K, Antrag.A, "1", "1", "1", PROJEKTNUMMER, ReportFormat.PDF);

        // When
        final Map<String, Object> parameters = reportAuswertungAntraegeMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters)
                .hasSize(17)
                .containsEntry("P_JAHR_VON", dto.jahrvon())
                .containsEntry("P_JAHR_BIS", dto.jahrbis())
                .containsEntry("P_BEZ", "1")
                .containsEntry("P_FB", "1")
                .containsEntry("P_UA", "11")
                .containsEntry("P_KURZ", "KB1")
                .containsEntry("P_PSTRASSE", "%Projektstraße 1%")
                .containsEntry("P_PNAME", "%Projekt 1%")
                .containsEntry("P_SGT", "1")
                .containsEntry("P_SBL", "SBL1")
                .containsEntry("P_BPG", "1")
                .containsEntry("P_FAG", "1")
                .containsEntry("P_PROJNR", "1234567")
                .containsEntry("P_OFFEN", "1")
                .containsEntry("P_KRISOFP", Krisofp.K.name())
                .containsEntry("P_ATYP", Antrag.A.name())
                .containsEntry("P_RELEVANT", "1");

    }
}
