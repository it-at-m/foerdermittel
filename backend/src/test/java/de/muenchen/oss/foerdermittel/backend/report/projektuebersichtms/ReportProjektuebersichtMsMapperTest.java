package de.muenchen.oss.foerdermittel.backend.report.projektuebersichtms;

import static de.muenchen.oss.foerdermittel.backend.projekt.Krisofp.X;
import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsDTO;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsMapper;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsSort;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportProjektuebersichtMsMapperTest {

    private final ReportProjektuebersichtMsMapper reportMapper = new ReportProjektuebersichtMsMapper();

    @Test
    void givenReportProjektuebersichtMsDTO_thenReturnsCorrectParameters() {
        // given
        final ReportProjektuebersichtMsDTO dto = new ReportProjektuebersichtMsDTO(
                "1999",
                "1",
                "1",
                "1",
                "1",
                "KB1",
                "0",
                "0",
                X,
                "1",
                "1",
                ReportFormat.PDF,
                ReportProjektuebersichtMsSort.PROJEKTNUMMER);

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(11)
                .containsEntry("P_JAHR", "1999")
                .containsEntry("P_SBL", "1")
                .containsEntry("P_BEZ", "1")
                .containsEntry("P_FB", "1")
                .containsEntry("P_UA", "1")
                .containsEntry("P_KURZ", "KB1")
                .containsEntry("P_OFFEN", "0")
                .containsEntry("P_VN", "0")
                .containsEntry("P_KRISOFP", "X")
                .containsEntry("P_SGT", "1")
                .containsEntry("P_BPG", "1");

    }

    @Test
    void givenReportProjektuebersichtMsDTOWithBlankValues_thenReturnsNullParameters() {
        // given
        final ReportProjektuebersichtMsDTO dto = new ReportProjektuebersichtMsDTO(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                "",
                "",
                null,
                null);

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(11)
                .containsEntry("P_JAHR", null)
                .containsEntry("P_SBL", null)
                .containsEntry("P_BEZ", null)
                .containsEntry("P_FB", null)
                .containsEntry("P_UA", null)
                .containsEntry("P_KURZ", null)
                .containsEntry("P_OFFEN", null)
                .containsEntry("P_VN", null)
                .containsEntry("P_KRISOFP", null)
                .containsEntry("P_SGT", null)
                .containsEntry("P_BPG", null);
    }

}
