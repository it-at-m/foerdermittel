package de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag;

import static de.muenchen.oss.foerdermittel.backend.report.ReportService.SORT_PARAMETER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.JasperReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
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
import org.mockito.Mock;

class FortsetzungsantragReportServiceTest {

    @Mock
    private FortsetzungsantragReportMapper reportMapper;

    @Mock
    private FortsetzungsantragReportService reportService;

    @Mock
    private ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;

    @Mock
    private StadtbezirkService stadtbezirkService;

    @Mock
    private JasperReportService jasperReportService;

    @Nested
    class GenerateReportFortsetzungsantrag {

        @Test
        void givenAllParameters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportFortsetzungsantragDTO parameters = new ReportFortsetzungsantragDTO(
                    "1",
                    "1",
                    "1",
                    "1",
                    ReportFormat.PDF);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            // When
            final GeneratedReport generatedReport = reportService.generateReportFortsetzungsantrag(parameters);

            // Then
            verify(listennameStadtbezirkslisteService, times(1))
                    .checkExistsByListenname("1");
            verify(stadtbezirkService, times(1))
                    .checkExistsByStadtbezirk(new BigDecimal("1"));
            verify(reportMapper, times(1))
                    .toJasperParameters(parameters);
            verifyNoInteractions(jasperReportService);

            assertThat(generatedReport).isNotNull();
            assertThat(generatedReport.contentType())
                    .isEqualTo(ReportFormat.PDF.getContentType());
            assertThat(generatedReport.fileName())
                    .startsWith(ReportType.FMW_BEWILL4.getFileName())
                    .endsWith(ReportFormat.PDF.getFileExtension());
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
            final List<StadtbezirkFormContextDTO> allStadtbezirke = List.of(new StadtbezirkFormContextDTO("1", "Test"),
                    new StadtbezirkFormContextDTO("2", "Test 2"), new StadtbezirkFormContextDTO("3", "Test 3"));
            when(stadtbezirkService.getStadtbezirkFormContextDTOs()).thenReturn(allStadtbezirke);

            // When
            final ReportFortsetzungsantragFormContext formContext = reportService.getReportFortsetzungsantrag();

            // Then
            verify(stadtbezirkService, times(1)).getStadtbezirkFormContextDTOs();
            assertThat(formContext.bezs()).isEqualTo(allStadtbezirke);
        }

    }
}
