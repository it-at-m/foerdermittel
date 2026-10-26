package de.muenchen.oss.foerdermittel.backend.report.staedtebaufoerderung;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.staedtebaufoerderung.dto.ReportStaedtebaufoerderungDTO;
import de.muenchen.oss.foerdermittel.backend.report.staedtebaufoerderung.dto.ReportStaedtebaufoerderungMapper;
import de.muenchen.oss.foerdermittel.backend.traeger.Traeger;
import de.muenchen.oss.foerdermittel.backend.traeger.TraegerService;
import de.muenchen.oss.foerdermittel.backend.traeger.dto.TraegerFormContextDTO;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReportStaedtebaufoerderungServiceTest {

    @Mock
    private TraegerService traegerService;

    @Mock
    private ReportService reportService;

    @Mock
    private ReportStaedtebaufoerderungMapper reportStaedtebaufoerderungMapper;

    @InjectMocks
    private ReportStaedtebaufoerderungService reportStaedtebaufoerderungService;

    @Nested
    class GenerateReportStaedtebaufoerderung {

        private ReportStaedtebaufoerderungDTO createStaedtebaufoerderungParameters() {
            return mock(ReportStaedtebaufoerderungDTO.class);
        }

        @Test
        void givenNoFilters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportStaedtebaufoerderungDTO parameters = createStaedtebaufoerderungParameters();
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(reportStaedtebaufoerderungMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_STAEDTEBAU,
                    ReportFormat.PDF, "ORDER BY bdat ASC"))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportStaedtebaufoerderungService.generateReportStaedtebaufoerderung(parameters);

            // Then
            verify(reportStaedtebaufoerderungMapper)
                    .toJasperParameters(parameters);

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_STAEDTEBAU,
                    ReportFormat.PDF,
                    "ORDER BY bdat ASC");

            verifyNoInteractions(
                    traegerService);

            assertThat(generatedReport).isSameAs(expectedReport);
        }

        @Test
        void givenValidParameters_thenShouldDelegateReportGeneration() {
            // Given
            final ReportStaedtebaufoerderungDTO parameters = createStaedtebaufoerderungParameters();
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(reportStaedtebaufoerderungMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_STAEDTEBAU,
                    ReportFormat.PDF,
                    "ORDER BY bdat ASC"))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport actualReport = reportStaedtebaufoerderungService.generateReportStaedtebaufoerderung(parameters);

            // Then
            assertThat(actualReport).isSameAs(expectedReport);
        }

        @Test
        void givenTraeger_thenShouldCheckTraeger() {
            // Given
            final ReportStaedtebaufoerderungDTO parameters = createStaedtebaufoerderungParameters();
            when(parameters.traeger()).thenReturn("333");

            when(reportStaedtebaufoerderungMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportStaedtebaufoerderungService.generateReportStaedtebaufoerderung(parameters);

            // Then
            verify(traegerService)
                    .checkExistsByTraeger(new BigDecimal("333"));
        }

        @Test
        void givenEmptyFilters_thenShouldNotCheckAnyFilterService() {
            // Given
            final ReportStaedtebaufoerderungDTO parameters = createStaedtebaufoerderungParameters();

            when(parameters.traeger()).thenReturn("");

            when(reportStaedtebaufoerderungMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportStaedtebaufoerderungService.generateReportStaedtebaufoerderung(parameters);

            // Then
            verifyNoInteractions(traegerService);
        }

        @Test
        void givenFoerderbereichNotFound_thenShouldPropagateException() {
            // Given
            final ReportStaedtebaufoerderungDTO parameters = mock(ReportStaedtebaufoerderungDTO.class);

            when(parameters.traeger()).thenReturn("123");

            final NotFoundException exception = new NotFoundException(Traeger.class, "123");

            doThrow(exception)
                    .when(traegerService)
                    .checkExistsByTraeger(new BigDecimal("123"));

            // When
            final Exception thrown = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportStaedtebaufoerderungService
                            .generateReportStaedtebaufoerderung(parameters));

            // Then
            assertThat(thrown).isSameAs(exception);

            verify(traegerService)
                    .checkExistsByTraeger(new BigDecimal("123"));

            verifyNoInteractions(reportStaedtebaufoerderungMapper);
        }
    }

    @Nested
    class GetReportStaedtbaufoerderungFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<TraegerFormContextDTO> traegerFormContextDTO = List.of(mock(TraegerFormContextDTO.class));

            when(traegerService.getTraegerFormContextDTOs()).thenReturn(traegerFormContextDTO);

            // When
            final ReportStaedtebaufoerderungFormContext formContext = reportStaedtebaufoerderungService.getReportStaedtebaufoerderung();

            // Then
            verify(traegerService).getTraegerFormContextDTOs();

            assertThat(formContext).isNotNull();
        }
    }
}
