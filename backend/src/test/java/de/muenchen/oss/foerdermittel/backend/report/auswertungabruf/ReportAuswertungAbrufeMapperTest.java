package de.muenchen.oss.foerdermittel.backend.report.auswertungabruf;

import static de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto.ReportAuswertungAbrufeSort.FOERDERBEREICH_PROJEKTNUMMER_ADATUM;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto.ReportAuswertungAbrufeDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto.ReportAuswertungAbrufeMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportAuswertungAbrufeMapperTest {
    private final ReportAuswertungAbrufeMapper reportAuswertungAbrufeMapper = new ReportAuswertungAbrufeMapper();

    @Test
    void givenReportAuswertungAbrufeDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportAuswertungAbrufeDTO dto = new ReportAuswertungAbrufeDTO(
                "1998",
                "2020",
                "1991",
                "2021",
                "1",
                "1",
                "11",
                "KB1",
                "Projektstraße 1",
                "Projekt 1",
                "1234567",
                "1",
                "SBL1",
                "1",
                Krisofp.K,
                "1",
                "1",
                "1",
                "1",
                "1",
                "1",
                "0",
                FOERDERBEREICH_PROJEKTNUMMER_ADATUM,
                ReportFormat.PDF);

        // When
        final Map<String, Object> parameters = reportAuswertungAbrufeMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters)
                .hasSize(22)
                .containsEntry("P_JAHR_A_VON", dto.jahrAbrufVon())
                .containsEntry("P_JAHR_A_BIS", dto.jahrAbrufBis())
                .containsEntry("P_JAHR_E_VON", dto.jahrErhaltVon())
                .containsEntry("P_JAHR_E_BIS", dto.jahrErhaltBis())
                .containsEntry("P_BEZ", "1")
                .containsEntry("P_FB", "1")
                .containsEntry("P_UA", "11")
                .containsEntry("P_KURZ", "KB1")
                .containsEntry("P_PSTRASSE", "%Projektstraße 1%")
                .containsEntry("P_PNAME", "%Projekt 1%")
                .containsEntry("P_PROJNR", "1234567")
                .containsEntry("P_SGT", "1")
                .containsEntry("P_SBL", "SBL1")
                .containsEntry("P_BPG", "1")
                .containsEntry("P_REF", "1")
                .containsEntry("P_KRH", "1")
                .containsEntry("P_FAG", "1")
                .containsEntry("P_OFFEN", "1")
                .containsEntry("P_OHNE_BEWILL", "1")
                .containsEntry("P_ERH", "1")
                .containsEntry("P_NICHT_ERH", "0")
                .containsEntry("P_KRISOFP", Krisofp.K.name());

    }
}
