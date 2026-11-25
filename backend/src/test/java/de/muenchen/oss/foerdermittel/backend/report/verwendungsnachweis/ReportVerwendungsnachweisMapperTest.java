package de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis;

import static de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.dto.ReportVerwendungsnachweisSort.PROJEKTNUMMER;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.dto.ReportVerwendungsnachweisDTO;
import de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.dto.ReportVerwendungsnachweisMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportVerwendungsnachweisMapperTest {

    private final ReportVerwendungsnachweisMapper reportVerwendungsnachweisMapper =
            new ReportVerwendungsnachweisMapper();

    @Test
    void givenReportVerwendungsnachweisDTO_thenReturnsCorrectParameters() {
        final ReportVerwendungsnachweisDTO dto = new ReportVerwendungsnachweisDTO(
                "2025",
                "01",
                "SBL1",
                "1",
                "11",
                "UA1",
                "KRH1",
                "Projekt 1",
                "Projektstraße 1",
                "KB1",
                "0",
                "0",
                "0",
                "0",
                Krisofp.K,
                "1",
                "1",
                ReportFormat.PDF,
                PROJEKTNUMMER);

        final Map<String, Object> parameters =
                reportVerwendungsnachweisMapper.toJasperParameters(dto);

        assertThat(parameters)
                .hasSize(17)
                .containsEntry("P_JAHR", "2025")
                .containsEntry("P_MONAT", "01")
                .containsEntry("P_BEZ", "1")
                .containsEntry("P_SBL", "SBL1")
                .containsEntry("P_FB", "11")
                .containsEntry("P_UA", "UA1")
                .containsEntry("P_KURZ", "KB1")
                .containsEntry("P_KRISOFP", Krisofp.K.name())
                .containsEntry("P_SGT", "1")
                .containsEntry("P_BPG", "1")
                .containsEntry("P_KRH", "KRH1")
                .containsEntry("P_PSTRASSE", "%Projektstraße 1%")
                .containsEntry("P_PNAME", "%Projekt 1%")
                .containsEntry("P_NOTIZ", "0")
                .containsEntry("P_OHNE_SCHLUSS", "0")
                .containsEntry("P_FAG", "0")
                .containsEntry("P_WIN", "0");
    }
}