package de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag.dto.ReportFortsetzungsantragDTO;
import de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag.dto.ReportFortsetzungsantragMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportFortsetzungsantragMapperTest {

    private final ReportFortsetzungsantragMapper reportMapper = new ReportFortsetzungsantragMapper();

    @Test
    void givenReportFortsetzungsantragDTO_thenReturnsCorrectParameters() {
        // given
        final ReportFortsetzungsantragDTO dto = new ReportFortsetzungsantragDTO(
                "SBL",
                "BEZ",
                "FAG",
                "1",
                ReportFormat.PDF);

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(4)
                .containsEntry("P_BEZ", "BEZ")
                .containsEntry("P_SBL", "SBL")
                .containsEntry("P_OFFEN", "1")
                .containsEntry("P_FAG", "FAG");
    }

    @Test
    void givenReportFortsetzungsantragDTOWithBlankValues_thenReturnsNullParameters() {
        // given
        final ReportFortsetzungsantragDTO dto = new ReportFortsetzungsantragDTO(
                "",
                "",
                "1",
                "",
                ReportFormat.PDF);

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(4)
                .containsEntry("P_BEZ", null)
                .containsEntry("P_SBL", null)
                .containsEntry("P_OFFEN", "")
                .containsEntry("P_FAG", "1");
    }

}
