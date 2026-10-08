package de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag;

import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportFortsetzungsantragDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportHaushalt1DTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportHaushalt1Sort;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportProjektuebersichtDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichworteDTO;
import de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag.dto.FortsetzungsantragReportMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FortsetzungsantragReportMapperTest {

    private final FortsetzungsantragReportMapper reportMapper = new FortsetzungsantragReportMapper();

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
