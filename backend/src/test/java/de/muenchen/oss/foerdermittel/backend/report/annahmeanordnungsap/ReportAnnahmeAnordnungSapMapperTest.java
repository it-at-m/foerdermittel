package de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.dto.ReportAnnahmeAnordnungSapDTO;
import de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.dto.ReportAnnahmeAnordnungSapMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportAnnahmeAnordnungSapMapperTest {

    private final ReportAnnahmeAnordnungSapMapper reportAnnahmeAnordnungSapMapper = new ReportAnnahmeAnordnungSapMapper();

    @Test
    void givenReportAnnahmeAnordnungSapDTO_thenReturnsCorrectParameters() {
        // Given
        final ReportAnnahmeAnordnungSapDTO dto = new ReportAnnahmeAnordnungSapDTO("P-123");

        // When
        final Map<String, Object> parameters = reportAnnahmeAnordnungSapMapper.toJasperParameters(dto);

        // Then
        assertThat(parameters)
                .hasSize(1)
                .containsEntry("P_PROJNR", dto.projnr());
    }


}
