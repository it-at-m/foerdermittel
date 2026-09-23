package de.muenchen.oss.foerdermittel.backend.report;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.dto.ReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichworteDTO;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportMapperTest {

    private final ReportMapper reportMapper = new ReportMapper();

    @Test
    void givenReportStichworteDTO_thenReturnsCorrectParameters() {
        // given
        final ReportStichworteDTO dto = new ReportStichworteDTO("TEST");

        // when
        Map<String, Object> parameters = reportMapper.toJasperParameters(dto);

        // then
        assertThat(parameters).hasSize(1).containsEntry("P_BEREICH", dto.bereich());
    }

}
