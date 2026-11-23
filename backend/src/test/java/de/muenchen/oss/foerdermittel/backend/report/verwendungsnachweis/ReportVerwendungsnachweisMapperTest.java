package de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.dto.ReportVerwendungsnachweisDTO;
import de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.dto.ReportVerwendungsnachweisMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.dto.ReportVerwendungsnachweisSort.PROJEKTNUMMER;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportVerwendungsnachweisMapperTest {
    private final ReportVerwendungsnachweisMapper reportVerwendungsnachweisMapper = new ReportVerwendungsnachweisMapper();

    @Test
    void givenReportVerwendungsnachweisDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportVerwendungsnachweisDTO dto = new ReportVerwendungsnachweisDTO("2025", "1", "1", "11", "KB1", "Projektstraße 1", "Projekt 1", "1", "SBL1",
                "1", "0", "0", "0", "0",Krisofp.K,"s","s", ReportFormat.PDF,PROJEKTNUMMER);

        // When
        final Map<String, Object> parameters = reportVerwendungsnachweisMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters)
                .hasSize(14)
                .containsEntry("P_JAHR", dto.jahr())
                .containsEntry("P_MONAT", dto.monat())
                .containsEntry("P_BEZ", "1")
                .containsEntry("P_FB", "1")
                .containsEntry("P_UA", "11")
                .containsEntry("P_KURZ", "KB1")
                .containsEntry("P_PSTRASSE", "%Projektstraße 1%")
                .containsEntry("P_PNAME", "%Projekt 1%")
                .containsEntry("P_SGT", "1")
                .containsEntry("P_SBL", "SBL1")
                .containsEntry("P_BPG", "1")
                .containsEntry("P_KRH", "1")
                .containsEntry("P_NOTIZEN", "0")
                .containsEntry("P_OHNE_SCHLUSS", "0")
                .containsEntry("P_KRISOFP", Krisofp.K.name())
                .containsEntry("P_FAG", "0")
                .containsEntry("P_WIN", "0");
    }

}
