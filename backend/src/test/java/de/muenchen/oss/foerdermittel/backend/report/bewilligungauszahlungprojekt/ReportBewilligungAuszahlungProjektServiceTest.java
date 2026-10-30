package de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportBewilligungAuszahlungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt.dto.ReportBewilligungAuszahlungProjektDTO;
import de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt.dto.ReportBewilligungAuszahlungProjektMapper;
import de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt.formcontext.ReportBewilligungAuszahlungProjektFormContext;
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
class ReportBewilligungAuszahlungProjektServiceTest {

    private static final String SORT = "";

    @Mock
    private ProjektService projektService;

    @Mock
    private ReportService reportService;

    @Mock
    private ReportBewilligungAuszahlungProjektMapper reportBewilligungAuszahlungProjektMapper;

    @InjectMocks
    private ReportBewilligungAuszahlungProjektService reportBewilligungAuszahlungProjektService;

    @Nested
    class GenerateReportBewilligungAuszahlungProjekt {

        @Test
        void givenExistingProjekt_thenAddsDatabaseValuesToJasperParameters() {
            // Given
            final ReportBewilligungAuszahlungProjektDTO parameters = new ReportBewilligungAuszahlungProjektDTO("P-123", true);
            final Map<String, Object> jasperParameters = new HashMap<>();
            final Projekt projekt = mock(Projekt.class);
            final GeneratedReport expectedReport = new GeneratedReport(
                    "test.pdf",
                    ReportFormat.PDF.getContentType(),
                    outputStream -> {
                    });

            when(reportBewilligungAuszahlungProjektMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);
            when(projektService.getProjekt(parameters.projnr())).thenReturn(projekt);
            when(projekt.getPname()).thenReturn("Projektname");
            when(projekt.getPstrasse()).thenReturn("Projektstraße 1");
            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE1,
                    ReportFormat.PDF,
                    null))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportBewilligungAuszahlungProjektService.generateReportBewilligungAuszahlungProjekt(parameters);

            // Then
            assertThat(generatedReport).isSameAs(expectedReport);
            assertThat(jasperParameters)
                    .containsEntry("P_PNAME", "Projektname")
                    .containsEntry("P_PSTRASSE", "Projektstraße 1");

            verify(projektService, times(1)).getProjekt(parameters.projnr());
            verify(reportBewilligungAuszahlungProjektMapper, times(1))
                    .toJasperParameters(parameters);
            verify(reportService, times(1)).generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE1,
                    ReportFormat.PDF,
                    null);
        }

        @Test
        void givenGeneratedReportIsWritten_thenUsesBewilligungAuszahlungProjektReport()
                throws JRException, SQLException, IOException {
            // Given
            final ReportBewilligungAuszahlungProjektDTO parameters = new ReportBewilligungAuszahlungProjektDTO("P-123", false);
            final Map<String, Object> jasperParameters = new HashMap<>();
            final Projekt projekt = mock(Projekt.class);
            final OutputStream outputStream = new ByteArrayOutputStream();
            final GeneratedReport expectedReport = new GeneratedReport(
                    "test.pdf",
                    ReportFormat.PDF.getContentType(),
                    ignored -> {
                    });

            when(reportBewilligungAuszahlungProjektMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);
            when(projektService.getProjekt(parameters.projnr())).thenReturn(projekt);
            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE1,
                    ReportFormat.PDF,
                    null))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportBewilligungAuszahlungProjektService.generateReportBewilligungAuszahlungProjekt(parameters);
            generatedReport.writer().write(outputStream);

            // Then
            verify(reportService, times(1)).generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE1,
                    ReportFormat.PDF,
                    null);
        }

        @Test
        void givenMissingProjekt_thenDoesNotGenerateReport() {
            // Given
            final ReportBewilligungAuszahlungProjektDTO parameters = new ReportBewilligungAuszahlungProjektDTO("MISSING", false);

            when(projektService.getProjekt(parameters.projnr()))
                    .thenThrow(new NotFoundException(Projekt.class, parameters.projnr()));

            // When / Then
            Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportBewilligungAuszahlungProjektService.generateReportBewilligungAuszahlungProjekt(parameters));

            verify(projektService, times(1)).getProjekt(parameters.projnr());
            verifyNoInteractions(reportService, reportBewilligungAuszahlungProjektMapper);
        }
    }

    @Nested
    class GetReportBewilligungAuszahlungProjektFormContext {

        @Test
        void givenProjectsExist_thenReturnsTheirFormContext() {
            // Given
            final List<ReportBewilligungAuszahlungProjektFormContextDTO> projekte = List.of(
                    new ReportBewilligungAuszahlungProjektFormContextDTO(
                            "P-123", "Projektname", "Projektstraße 1"));

            when(projektService.getReportBewilligungAuszahlungProjektFormContextDTOs())
                    .thenReturn(projekte);

            // When
            final ReportBewilligungAuszahlungProjektFormContext formContext = reportBewilligungAuszahlungProjektService.getReportBewilligungAuszahlungProjekt();

            // Then
            assertThat(formContext.projekte()).isEqualTo(projekte);
            verify(projektService, times(1))
                    .getReportBewilligungAuszahlungProjektFormContextDTOs();
            verifyNoInteractions(reportService, reportBewilligungAuszahlungProjektMapper);
        }
    }
}
