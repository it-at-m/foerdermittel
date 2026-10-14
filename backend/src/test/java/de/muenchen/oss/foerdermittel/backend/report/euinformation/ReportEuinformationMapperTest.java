package de.muenchen.oss.foerdermittel.backend.report.euinformation;

import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportFortsetzungsantragDTO;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.ReportEuinformationDTO;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.ReportEuinformationMapper;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.ReportEuinformationSort;
import de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag.dto.FortsetzungsantragReportMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ReportEuinformationMapperTest {

    private final ReportEuinformationMapper reportMapper = new ReportEuinformationMapper();

    @Test
    void givenReportEuinformationDTO_thenReturnsCorrectParameters() {
        // given
        final ReportEuinformationDTO dto = new ReportEuinformationDTO(
                "1999",
                "2010",
                "Test",
                "1",
                "1",
                ReportEuinformationSort.REFERAT);

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(6)
                .containsEntry("P_JAHR_VON", "1999")
                .containsEntry("P_JAHR_BIS","2010")
                .containsEntry("P_INHALT", "%Test%")
                .containsEntry("P_PUB", "1")
                .containsEntry("P_REFID", "1")
                .containsEntry("P_REFBEZ", "RAW");
    }

    @Test
    void givenReportEuinformationDTOWithBlankValues_thenReturnsNullParameters() {
        // given
        final ReportEuinformationDTO dto = new ReportEuinformationDTO(
                "",
                "",
                "",
                "",
                "",
                null);

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(6)
                .containsEntry("P_JAHR_VON", null)
                .containsEntry("P_JAHR_BIS", null)
                .containsEntry("P_INHALT", null)
                .containsEntry("P_PUB", "")
                .containsEntry("P_REFID", "")
                .containsEntry("P_REFBEZ", null);
    }

}
