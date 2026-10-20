package de.muenchen.oss.foerdermittel.backend.report.projektuebersichtms;

import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsDTO;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsMapper;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsSort;
import de.muenchen.oss.foerdermittel.backend.report.projektuebersichtms.dto.ReportProjektuebersichtMsDTO;
import de.muenchen.oss.foerdermittel.backend.report.projektuebersichtms.dto.ReportProjektuebersichtMsMapper;
import de.muenchen.oss.foerdermittel.backend.report.projektuebersichtms.dto.ReportProjektuebersichtMsSort;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ReportProjektuebersichtMsMapperTest {

    private final ReportProjektuebersichtMsMapper reportMapper = new ReportProjektuebersichtMsMapper();

    @Test
    void givenReportProjektuebersichtMsDTO_thenReturnsCorrectParameters() {
        // given
        final ReportProjektuebersichtMsDTO dto = new ReportProjektuebersichtMsDTO(
                "1999",
                "2010",
                "Test",
                "1",
                "1",
                ReportProjektuebersichtMsSort.REFERAT);

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(6)
                .containsEntry("P_JAHR_VON", "1999")
                .containsEntry("P_JAHR_BIS", "2010")
                .containsEntry("P_INHALT", "%Test%")
                .containsEntry("P_PUB", "1")
                .containsEntry("P_REFID", "1")
                .containsEntry("P_REFBEZ", "RAW");
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
                null);

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(6)
                .containsEntry("P_JAHR_VON", null)
                .containsEntry("P_JAHR_BIS", null)
                .containsEntry("P_INHALT", null)
                .containsEntry("P_PUB", null)
                .containsEntry("P_REFID", "")
                .containsEntry("P_REFBEZ", null);
    }

}
