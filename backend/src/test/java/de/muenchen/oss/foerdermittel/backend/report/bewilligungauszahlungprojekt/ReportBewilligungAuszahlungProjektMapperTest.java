package de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt.dto.ReportBewilligungAuszahlungProjektDTO;
import de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt.dto.ReportBewilligungAuszahlungProjektMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportBewilligungAuszahlungProjektMapperTest {

    private final ReportBewilligungAuszahlungProjektMapper reportBewilligungAuszahlungProjektMapper = new ReportBewilligungAuszahlungProjektMapper();

    @Test
    void givenReportBewilligungAuszahlungProjektDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportBewilligungAuszahlungProjektDTO dto = new ReportBewilligungAuszahlungProjektDTO("P-123", true);

        // When
        final Map<String, Object> parameters = reportBewilligungAuszahlungProjektMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters)
                .hasSize(2)
                .containsEntry("P_PROJNR", dto.projnr())
                .containsEntry("P_ERFOLGT", "1");
    }

    @Test
    void givenReportBewilligungAuszahlungProjektDTOWithoutErfolgt_thenMapsFalseToZero() {
        // Given
        final ReportBewilligungAuszahlungProjektDTO dto = new ReportBewilligungAuszahlungProjektDTO("P-123", false);

        // When
        final Map<String, Object> parameters = reportBewilligungAuszahlungProjektMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters).containsEntry("P_ERFOLGT", "0");
    }

}
