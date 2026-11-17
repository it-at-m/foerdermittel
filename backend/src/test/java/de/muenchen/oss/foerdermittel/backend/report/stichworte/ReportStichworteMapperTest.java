package de.muenchen.oss.foerdermittel.backend.report.stichworte;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.stichworte.dto.ReportStichworteDTO;
import de.muenchen.oss.foerdermittel.backend.report.stichworte.dto.ReportStichworteMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportStichworteMapperTest {

    private final ReportStichworteMapper reportStichworteMapper = new ReportStichworteMapper();

    @Test
    void givenReportStichworteDTO_thenReturnsCorrectParameters() {
        // given
        final ReportStichworteDTO dto = new ReportStichworteDTO("TEST");

        // when
        final Map<String, Object> parameters = reportStichworteMapper.toJasperParameters(dto);

        // then
        assertThat(parameters)
                .hasSize(1)
                .containsEntry("P_BEREICH", dto.bereich());
    }

}
