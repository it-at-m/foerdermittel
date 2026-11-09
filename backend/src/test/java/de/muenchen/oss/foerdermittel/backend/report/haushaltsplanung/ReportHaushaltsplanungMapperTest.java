package de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.dto.ReportHaushaltsplanungDTO;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.dto.ReportHaushaltsplanungMapper;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.dto.ReportHaushaltsplanungSort;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportHaushaltsplanungMapperTest {

    private final ReportHaushaltsplanungMapper reportHaushaltsplanungMapper = new ReportHaushaltsplanungMapper();

    @Test
    void givenReportHaushaltDTO_thenReturnsCorrectParameters() {
        // given
        final ReportHaushaltsplanungDTO dto = new ReportHaushaltsplanungDTO(
                "2026",
                "FB",
                "FIPO",
                "SBL",
                "BEZ",
                "1",
                ReportHaushaltsplanungSort.FB_PROJEKTNUMMER,
                ReportFormat.PDF);

        // when
        final Map<String, Object> parameters = reportHaushaltsplanungMapper.toJasperParameters(dto);

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
        final ReportHaushaltsplanungDTO dto = new ReportHaushaltsplanungDTO(
                "2026",
                "",
                "",
                null,
                "",
                "0",
                ReportHaushaltsplanungSort.FB_PROJEKTNUMMER,
                ReportFormat.PDF);

        // when
        final Map<String, Object> parameters = reportHaushaltsplanungMapper.toJasperParameters(dto);

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

}
