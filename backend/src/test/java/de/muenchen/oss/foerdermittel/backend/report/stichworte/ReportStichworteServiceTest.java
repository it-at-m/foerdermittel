
package de.muenchen.oss.foerdermittel.backend.report.stichworte;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.stichworte.dto.ReportStichworteDTO;
import de.muenchen.oss.foerdermittel.backend.report.stichworte.dto.ReportStichworteMapper;
import de.muenchen.oss.foerdermittel.backend.report.stichworte.formcontext.ReportStichworteFormContext;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.Stichwortbereich;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.StichwortbereichService;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichFormContextDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportStichworteServiceTest {

    private static final String BEREICH = "TEST";
    private static final String SORT = "ORDER BY stb_bereich ASC, nr ASC, wort ASC";

    @Mock
    private StichwortbereichService stichwortbereichService;

    @Mock
    private ReportService reportService;

    @Mock
    private ReportStichworteMapper reportStichworteMapper;

    @InjectMocks
    private ReportStichworteService reportStichworteService;

    @Nested
    class GenerateReportStichworte {

        @Test
        void givenValidParameters_thenShouldReturnGeneratedReport() {
            // Given
            final ReportStichworteDTO parameters = new ReportStichworteDTO(BEREICH);
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = new GeneratedReport(
                    "test.pdf",
                    ReportFormat.PDF.getContentType(),
                    outputStream -> {
                    });

            when(reportStichworteMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);
            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_ABLAGEINDEX,
                    ReportFormat.PDF,
                    SORT))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport actualReport = reportStichworteService.generateReportStichworte(parameters);

            // Then
            verify(stichwortbereichService, times(1)).checkExistsByBereich(BEREICH);
            verify(reportStichworteMapper, times(1)).toJasperParameters(parameters);
            verify(reportService, times(1)).generateReport(
                    jasperParameters,
                    ReportType.FMW_ABLAGEINDEX,
                    ReportFormat.PDF,
                    SORT);

            assertThat(actualReport).isSameAs(expectedReport);
            assertThat(actualReport.contentType()).isEqualTo(ReportFormat.PDF.getContentType());
            assertThat(actualReport.fileName()).isEqualTo("test.pdf");
        }

        @Test
        void givenValidParameters_thenShouldCallReportWriter()
                throws JRException, SQLException, IOException {
            // Given
            final ReportStichworteDTO parameters = new ReportStichworteDTO(BEREICH);
            final Map<String, Object> jasperParameters = new HashMap<>();
            final OutputStream outputStream = new ByteArrayOutputStream();
            final GeneratedReport expectedReport = new GeneratedReport(
                    "test.pdf",
                    ReportFormat.PDF.getContentType(),
                    ignored -> {
                    });

            when(reportStichworteMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);
            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_ABLAGEINDEX,
                    ReportFormat.PDF,
                    SORT))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportStichworteService.generateReportStichworte(parameters);
            generatedReport.writer().write(outputStream);

            // Then
            verify(stichwortbereichService, times(1)).checkExistsByBereich(BEREICH);
            verify(reportStichworteMapper, times(1)).toJasperParameters(parameters);
            verify(reportService, times(1)).generateReport(
                    jasperParameters,
                    ReportType.FMW_ABLAGEINDEX,
                    ReportFormat.PDF,
                    SORT);
        }

        @Test
        void givenNotFound_thenShouldThrowNotFoundException() {
            // Given
            final String bereich = "test";
            final ReportStichworteDTO parameters = new ReportStichworteDTO(bereich);

            doThrow(new NotFoundException(Stichwortbereich.class, bereich))
                    .when(stichwortbereichService)
                    .checkExistsByBereich(bereich);

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportStichworteService.generateReportStichworte(parameters));

            // Then
            verify(stichwortbereichService, times(1)).checkExistsByBereich(bereich);
            verifyNoInteractions(reportStichworteMapper, reportService);

            assertThat(exception.getMessage()).isEqualTo(
                    String.format(
                            "The %s with ID %s was not found.",
                            Stichwortbereich.class.getSimpleName(),
                            bereich));
        }
    }

    @Nested
    class GetReportStichworteFormContext {

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // Given
            final List<StichwortbereichFormContextDTO> allBereiche = List.of(
                    new StichwortbereichFormContextDTO("K", "Test"),
                    new StichwortbereichFormContextDTO("L", "Test 2"),
                    new StichwortbereichFormContextDTO("M", "Test 3"));

            when(stichwortbereichService.getStichwortbereichFormContextDTOs())
                    .thenReturn(allBereiche);

            // When
            final ReportStichworteFormContext formContext = reportStichworteService.getReportStichworte();

            // Then
            verify(stichwortbereichService, times(1))
                    .getStichwortbereichFormContextDTOs();

            assertThat(formContext.bereiche()).isEqualTo(allBereiche);
        }
    }
}
