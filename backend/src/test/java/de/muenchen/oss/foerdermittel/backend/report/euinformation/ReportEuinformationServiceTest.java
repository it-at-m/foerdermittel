package de.muenchen.oss.foerdermittel.backend.report.euinformation;

import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.ReportEuinformationDTO;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.ReportEuinformationMapper;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.formcontext.ReportEuinformationFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.muenchen.oss.foerdermittel.backend.report.ReportService.SORT_PARAMETER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportEuinformationServiceTest {

    @Mock
    private ReportEuinformationMapper reportMapper;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportEuinformationService euinformationReportService;

    @Nested
    class GenerateReportEuinformation {

        @Test
        void givenAllParameters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final String sbl = "1";
            final ReportEuinformationDTO parameters = new ReportEuinformationDTO(
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
            final GeneratedReport generatedReport = euinformationReportService
                    .generateReportEuinformation(parameters);

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
    class GetReportEuinformationFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<StadtbezirkFormContextDTO> allStadtbezirke = List.of(
                    new StadtbezirkFormContextDTO("1", "Test"),
                    new StadtbezirkFormContextDTO("2", "Test 2"),
                    new StadtbezirkFormContextDTO("3", "Test 3"));

            when(stadtbezirkService.getStadtbezirkFormContextDTOs())
                    .thenReturn(allStadtbezirke);

            // When
            final ReportEuinformationFormContext formContext = euinformationReportService
                    .getReportEuinformation();

            // Then
            verify(stadtbezirkService, times(1))
                    .getStadtbezirkFormContextDTOs();

            assertThat(formContext.bezs()).isEqualTo(allStadtbezirke);
        }
    }
}
