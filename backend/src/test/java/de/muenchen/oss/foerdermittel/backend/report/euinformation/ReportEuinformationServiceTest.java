package de.muenchen.oss.foerdermittel.backend.report.euinformation;

import static de.muenchen.oss.foerdermittel.backend.report.ReportService.SORT_PARAMETER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.publikation.PublikationService;
import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.ReportEuinformationDTO;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.ReportEuinformationMapper;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.ReportEuinformationSort;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.formcontext.ReportEuinformationFormContext;
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
class ReportEuinformationServiceTest {

    @Mock
    private ReportEuinformationMapper reportMapper;

    @Mock
    private PublikationService publikationService;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportEuinformationService reportEuinformationService;

    @Nested
    class GenerateReportEuinformation {

        @Test
        void givenAllParameters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final String jahrVon = "1998";
            final String jahrBis = "2010";
            final ReportEuinformationDTO parameters = new ReportEuinformationDTO(
                    jahrVon, jahrBis, "Test", "1", "1", ReportEuinformationSort.REFERAT);

            final Map<String, Object> jasperParameters = new HashMap<>();
            jasperParameters.put(
                    SORT_PARAMETER,
                    ReportEuinformationSort.REFERAT);

            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_EUINFORMATIONEN, ReportFormat.PDF, ReportEuinformationSort.REFERAT.getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportEuinformationService
                    .generateReportEuinformationen(parameters);

            // Then
            verify(reportMapper, times(1))
                    .toJasperParameters(parameters);

            verify(reportService, times(1)).generateReport(
                    jasperParameters,
                    ReportType.FMW_EUINFORMATIONEN,
                    ReportFormat.PDF,
                    ReportEuinformationSort.REFERAT.getOrderBy());

            assertThat(generatedReport).isSameAs(expectedReport);
            assertThat(jasperParameters)
                    .containsEntry(
                            SORT_PARAMETER,
                            ReportEuinformationSort.REFERAT);
        }
    }

    @Nested
    class GetReportEuinformationFormContext {

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
            final ReportEuinformationFormContext formContext = reportEuinformationService
                    .getReportEuinformationen();

            // Then
            verify(publikationService, times(1))
                    .getPublikationFormContextDTOs();

            assertThat(formContext.publikationen()).isEqualTo(allPublikationen);
        }
    }
}
