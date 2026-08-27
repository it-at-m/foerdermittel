package de.muenchen.oss.foerdermittel.backend.report;

import static de.muenchen.oss.foerdermittel.backend.report.ReportService.SORT_PARAMETER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.report.dto.ReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichwortbereicheDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private JasperReportService jasperReportService;

    @Mock
    private ReportMapper reportMapper;

    @InjectMocks
    private ReportService reportService;

    @Nested
    class GenerateReportStichwortbereiche {

        @Test
        void givenNoWriteInteraction_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportStichwortbereicheDTO parameters = mock(ReportStichwortbereicheDTO.class);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            // When
            final GeneratedReport generatedReport = reportService.generateReportStichwortbereiche(parameters);

            // Then
            verify(reportMapper, times(1)).toJasperParameters(parameters);
            verifyNoInteractions(jasperReportService);

            assertThat(generatedReport).isNotNull();
            assertThat(generatedReport.contentType())
                    .isEqualTo(ReportFormat.PDF.getContentType());
            assertThat(generatedReport.fileName())
                    .startsWith(ReportType.FMW_ABLAGEINDEX.getFileName())
                    .endsWith(ReportFormat.PDF.getFileExtension());
            assertThat(jasperParameters)
                    .containsEntry(
                            SORT_PARAMETER,
                            "ORDER BY stb_bereich ASC, nr ASC, wort ASC");
        }

        @Test
        void givenWriteInteraction_thenShouldCallJasperServiceCorrectly() throws JRException, SQLException, IOException {
            // Given
            final ReportStichwortbereicheDTO parameters = mock(ReportStichwortbereicheDTO.class);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            final OutputStream outputStream = new ByteArrayOutputStream();

            // When
            final GeneratedReport generatedReport = reportService.generateReportStichwortbereiche(parameters);
            generatedReport.writer().write(outputStream);

            // Then
            verify(jasperReportService, times(1)).generateReportWithParameters(
                    ReportType.FMW_ABLAGEINDEX,
                    ReportFormat.PDF,
                    jasperParameters,
                    outputStream);
        }

    }

}
