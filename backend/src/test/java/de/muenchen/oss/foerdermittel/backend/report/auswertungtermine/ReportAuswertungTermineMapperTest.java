package de.muenchen.oss.foerdermittel.backend.report.auswertungtermine;

import static de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto.ReportAuswertungTermineSort.PROJEKTNUMMER;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto.ReportAuswertungTermineDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto.ReportAuswertungTermineMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportAuswertungTermineMapperTest {
    private final ReportAuswertungTermineMapper reportAuswertungTermineMapper = new ReportAuswertungTermineMapper();

    @Test
    void givenReportAuswertungTermineDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportAuswertungTermineDTO dto = new ReportAuswertungTermineDTO(
                2023,
                12,
                "1",
                "1",
                "11",
                "KB1",
                "Projektstraße 1",
                "Projekt 1",
                "1",
                true,
                true,
                PROJEKTNUMMER);

        // When
        final Map<String, Object> parameters = reportAuswertungTermineMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters)
                .hasSize(11)
                .containsEntry("P_JAHR", dto.jahr().toString())
                .containsEntry("P_MONAT", dto.monat().toString())
                .containsEntry("P_BEZ", "1")
                .containsEntry("P_FB", "1")
                .containsEntry("P_UA", "11")
                .containsEntry("P_KURZ", "KB1")
                .containsEntry("P_PSTRASSE", "%Projektstraße 1%")
                .containsEntry("P_PNAME", "%Projekt 1%")
                .containsEntry("P_ZUKUNFT", "1")
                .containsEntry("P_UEB", "1");

    }
}
