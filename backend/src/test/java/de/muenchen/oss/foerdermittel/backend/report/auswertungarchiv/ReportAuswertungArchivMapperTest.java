package de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv;

import static de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto.ReportAuswertungArchivSort.STRASSE_PROJEKTNUMMER;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto.ReportAuswertungArchivDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto.ReportAuswertungArchivMapper;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportAuswertungArchivMapperTest {
    private final ReportAuswertungArchivMapper reportAuswertungArchivMapper = new ReportAuswertungArchivMapper();

    @Test
    void givenReportAuswertungArchivDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportAuswertungArchivDTO dto = new ReportAuswertungArchivDTO(
                "1",
                "1",
                "1",
                "KB1",
                "Projektstraße 1",
                "Projekt 1",
                "1234567",
                Krisofp.K,
                false,
                false,
                false,
                LocalDate.of(2026, 3, 12),
                LocalDate.of(2026, 3, 12),
                false,
                false,
                STRASSE_PROJEKTNUMMER);

        // When
        final Map<String, Object> parameters = reportAuswertungArchivMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters)
                .hasSize(15)
                .containsEntry("P_BEZ", "1")
                .containsEntry("P_FB", "1")
                .containsEntry("P_UA", "1")
                .containsEntry("P_KURZ", "KB1")
                .containsEntry("P_PSTRASSE", "%Projektstraße 1%")
                .containsEntry("P_PNAME", "%Projekt 1%")
                .containsEntry("P_FILM", "0")
                .containsEntry("P_FILM_OHNE", "0")
                .containsEntry("P_OFFEN", "0")
                .containsEntry("P_OP", "0")
                .containsEntry("P_MP", "0")
                .containsEntry("P_PROJNR", "1234567")
                .containsEntry("P_OFFEN", "0")
                .containsEntry("P_SP_SEIT", "12.03.2026")
                .containsEntry("P_PLAN_AB", "26.01.2026")
                .containsEntry("P_KRISOFP", Krisofp.K.name());

    }
}
