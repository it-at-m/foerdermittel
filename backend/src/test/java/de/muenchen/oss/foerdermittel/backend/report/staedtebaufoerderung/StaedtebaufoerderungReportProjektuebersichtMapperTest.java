package de.muenchen.oss.foerdermittel.backend.report.staedtebaufoerderung;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.staetebaufoerderung.dto.ReportStaedtebaufoerderungDTO;
import de.muenchen.oss.foerdermittel.backend.report.staetebaufoerderung.dto.ReportStaedtebaufoerderungMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class StaedtebaufoerderungReportProjektuebersichtMapperTest {

    private final ReportStaedtebaufoerderungMapper reportMapper = new ReportStaedtebaufoerderungMapper();

    @Test
    void givenReportStaedtebaufoerderungDTO_thenReturnsCorrectParameters() {
        // given
        final ReportStaedtebaufoerderungDTO dto = new ReportStaedtebaufoerderungDTO(
                "1999",
                "2010",
                "Test",
                "1",
                "1");

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(5)
                .containsEntry("P_JAHR_VON", "1999")
                .containsEntry("P_JAHR_BIS", "2010")
                .containsEntry("P_PNAME", "%Test%")
                .containsEntry("P_NOTIZ", "1")
                .containsEntry("P_TRAEGER", "1");
    }

    @Test
    void givenReportStaedtebaufoerderungDTOWithBlankValues_thenReturnsNullParameters() {
        // given
        final ReportStaedtebaufoerderungDTO dto = new ReportStaedtebaufoerderungDTO(
                "",
                "",
                "",
                "",
                "");

        // when
        final Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(5)
                .containsEntry("P_JAHR_VON", null)
                .containsEntry("P_JAHR_BIS", null)
                .containsEntry("P_NOTIZ", "")
                .containsEntry("P_TRAEGER", null)
                .containsEntry("P_PNAME", null);
    }
}
