package de.muenchen.oss.foerdermittel.backend.report.projektuebersichtms;

import de.muenchen.oss.foerdermittel.backend.publikation.PublikationService;
import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.ReportProjektuebersichtMsService;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsDTO;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsMapper;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsSort;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.formcontext.ReportProjektuebersichtMsFormContext;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportProjektuebersichtMsServiceTest {

    @Mock
    private ReportProjektuebersichtMsMapper reportMapper;

    @Mock
    private PublikationService publikationService;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportProjektuebersichtMsService reportProjektUebersichtMsService;

    @Nested
    class GenerateReportProjektUebersichtMs {

        @Test
        void givenAllParameters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final String jahrVon = "1998";
            final String jahrBis = "2010";
            final ReportProjektuebersichtMsDTO parameters = new ReportProjektuebersichtMsDTO(
                    jahrVon, jahrBis, "Test", "1", "1", ReportProjektuebersichtMsSort.REFERAT);

            final Map<String, Object> jasperParameters = new HashMap<>();

            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE4, ReportFormat.PDF, ReportProjektuebersichtMsSort.REFERAT.getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportProjektUebersichtMsService
                    .generateReportProjektuebersichtMs(parameters);

            // Then
            verify(reportMapper, times(1))
                    .toJasperParameters(parameters);

            verify(reportService, times(1)).generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE4,
                    ReportFormat.PDF,
                    ReportProjektuebersichtMsService.REFERAT.getOrderBy());

            assertThat(generatedReport).isSameAs(expectedReport);
        }
    }

    @Nested
    class GetReportProjektUebersichtMsFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<PublikationFormContextDTO> allPublikationen = List.of(
                    new PublikationFormContextDTO("1", "Test"),
                    new PublikationFormContextDTO("2", "Test 2"),
                    new PublikationFormContextDTO("3", "Test 3"));

            when(publikationService.getPublikationFormContextDTOs())
                    .thenReturn(allPublikationen);

            // When
            final ReportProjektuebersichtMsFormContext formContext = reportProjektUebersichtMsService
                    .getReportProjektuebersichtMs();

            // Then
            verify(publikationService, times(1))
                    .getPublikationFormContextDTOs();

            assertThat(formContext.publikationen()).isEqualTo(allPublikationen);
        }
    }
}
