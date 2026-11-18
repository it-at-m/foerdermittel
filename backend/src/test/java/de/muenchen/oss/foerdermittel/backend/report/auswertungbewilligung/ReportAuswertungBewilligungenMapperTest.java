package de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung;

import static de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.dto.ReportAuswertungBewilligungenSort.PROJEKTNUMMER_BEWILLIGTAM;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.Zuwendungsart;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.dto.ReportAuswertungBewilligungenDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.dto.ReportAuswertungBewilligungenMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportAuswertungBewilligungenMapperTest {
    private final ReportAuswertungBewilligungenMapper reportAuswertungBewilligungenMapper = new ReportAuswertungBewilligungenMapper();

    @Test
    void givenReportAuswertungBewilligungenDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportAuswertungBewilligungenDTO dto = new ReportAuswertungBewilligungenDTO("2023", "2025", "10", "17.11.2026", "1", "1", "11", "KB1",
                "Projektstraße 1", "Projekt 1",
                "1234567", "1",
                "SBL1", "1", Krisofp.K, Zuwendungsart.G, "1", "1", "1", PROJEKTNUMMER_BEWILLIGTAM, ReportFormat.PDF);

        // When
        final Map<String, Object> parameters = reportAuswertungBewilligungenMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters)
                .hasSize(19)
                .containsEntry("P_JAHR_VON", dto.jahrVon())
                .containsEntry("P_JAHR_BIS", dto.jahrBis())
                .containsEntry("P_MONAT", dto.monat())
                .containsEntry("P_BDATUM", dto.datum())
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
                .containsEntry("P_ERFOLGT", "1")
                .containsEntry("P_KRISOFP", Krisofp.K.name())
                .containsEntry("P_BZUWART", Zuwendungsart.G.name())
                .containsEntry("P_NOTIZ", "1");

    }
}
