package de.muenchen.oss.foerdermittel.backend.report;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.dto.ReportHaushalt1DTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportHaushalt1Sort;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportProjektuebersichtDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichworteDTO;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportMapperTest {

    private final ReportMapper reportMapper = new ReportMapper();

    @Test
    void givenReportStichworteDTO_thenReturnsCorrectParameters() {
        // given
        final ReportStichworteDTO dto = new ReportStichworteDTO("TEST");

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(1)
                .containsEntry("P_BEREICH", dto.bereich());
    }

    @Test
    void givenReportHaushaltDTO_thenReturnsCorrectParameters() {
        // given
        final ReportHaushalt1DTO dto = new ReportHaushalt1DTO(
                "2026",
                "FB",
                "FIPO",
                "SBL",
                "BEZ",
                "1",
                ReportHaushalt1Sort.FB_PROJEKTNUMMER,
                ReportFormat.PDF);

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(6)
                .containsEntry("P_JAHR", "2026")
                .containsEntry("P_FB", "FB")
                .containsEntry("P_FIPO", "FIPO")
                .containsEntry("P_SBL", "SBL")
                .containsEntry("P_BEZ", "BEZ")
                .containsEntry("P_HH", "1");

    }

    @Test
    void givenReportHaushaltDTOWithBlankValues_thenReturnsNullParameters() {
        // given
        final ReportHaushalt1DTO dto = new ReportHaushalt1DTO(
                "2026",
                "",
                "",
                null,
                "",
                "0",
                ReportHaushalt1Sort.FB_PROJEKTNUMMER,
                ReportFormat.PDF);

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(6)
                .containsEntry("P_JAHR", "2026")
                .containsEntry("P_FB", null)
                .containsEntry("P_FIPO", null)
                .containsEntry("P_SBL", null)
                .containsEntry("P_BEZ", null)
                .containsEntry("P_HH", "0");
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

}
