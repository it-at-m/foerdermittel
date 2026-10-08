package de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag;

import static de.muenchen.oss.foerdermittel.backend.report.ReportService.SORT_PARAMETER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportFortsetzungsantragDTO;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportFortsetzungsantragFormContext;
import de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag.dto.FortsetzungsantragReportMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FortsetzungsantragReportServiceTest {

    @Mock
    private FortsetzungsantragReportMapper reportMapper;

    @Mock
    private ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;

    @Mock
    private StadtbezirkService stadtbezirkService;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private FortsetzungsantragReportService fortsetzungsantragReportService;

    @Nested
    class GenerateReportFortsetzungsantrag {

        @Test
        void givenAllParameters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final String sbl = "1";
            final ReportFortsetzungsantragDTO parameters =
                    new ReportFortsetzungsantragDTO(
                            sbl, "1", "1", "1", ReportFormat.PDF);

            final Map<String, Object> jasperParameters = new HashMap<>();
            jasperParameters.put(
                    SORT_PARAMETER,
                    "order by v_fob_fb asc, v_projnr asc, v_bdatum asc");

            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_BEWILL4,
                    parameters.type(),
                    "order by v_fob_fb asc, v_projnr asc, v_bdatum asc"))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport =
                    fortsetzungsantragReportService
                            .generateReportFortsetzungsantrag(parameters);

            // Then
            verify(listennameStadtbezirkslisteService, times(1))
                    .checkExistsByListenname(sbl);

            verify(stadtbezirkService, times(1))
                    .checkExistsByStadtbezirk(new BigDecimal("1"));

            verify(reportMapper, times(1))
                    .toJasperParameters(parameters);

            verify(reportService, times(1)).generateReport(
                    jasperParameters,
                    ReportType.FMW_BEWILL4,
                    parameters.type(),
                    "order by v_fob_fb asc, v_projnr asc, v_bdatum asc");

            assertThat(generatedReport).isSameAs(expectedReport);
            assertThat(jasperParameters)
                    .containsEntry(
                            SORT_PARAMETER,
                            "order by v_fob_fb asc, v_projnr asc, v_bdatum asc");
        }
    }

    @Nested
    class GetReportFortsetzungsantragFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<StadtbezirkFormContextDTO> allStadtbezirke =
                    List.of(
                            new StadtbezirkFormContextDTO("1", "Test"),
                            new StadtbezirkFormContextDTO("2", "Test 2"),
                            new StadtbezirkFormContextDTO("3", "Test 3"));

            when(stadtbezirkService.getStadtbezirkFormContextDTOs())
                    .thenReturn(allStadtbezirke);

            // When
            final ReportFortsetzungsantragFormContext formContext =
                    fortsetzungsantragReportService
                            .getReportFortsetzungsantrag();

            // Then
            verify(stadtbezirkService, times(1))
                    .getStadtbezirkFormContextDTOs();

            assertThat(formContext.bezs()).isEqualTo(allStadtbezirke);
        }
    }
}
