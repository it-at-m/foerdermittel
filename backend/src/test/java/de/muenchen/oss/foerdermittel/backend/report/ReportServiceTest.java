package de.muenchen.oss.foerdermittel.backend.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private JasperReportService jasperReportService;

    @InjectMocks
    private ReportService reportService;

    private ReportType reportType;
    private ReportFormat reportFormat;

    @BeforeEach
    void setUp() {
        reportType = Arrays.stream(ReportType.values())
                .filter(type -> Arrays.stream(ReportFormat.values())
                        .anyMatch(type::supportsFormat))
                .findFirst()
                .orElseThrow();

        reportFormat = Arrays.stream(ReportFormat.values())
                .filter(reportType::supportsFormat)
                .findFirst()
                .orElseThrow();
    }

    @Test
    void givenSort_thenSetsSortParameter() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("existing", "value");

        final GeneratedReport result = reportService.generateReport(parameters, reportType, reportFormat, "jahr");

        assertThat(result).isNotNull();
        assertThat(parameters)
                .containsEntry(ReportService.SORT_PARAMETER, "jahr");
    }

    @Test
    void givenNullSort_thenRemovesSortParameter() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(ReportService.SORT_PARAMETER, "old-sort");

        final GeneratedReport result = reportService.generateReport(parameters, reportType, reportFormat, null);

        assertThat(result).isNotNull();
        assertThat(parameters)
                .doesNotContainKey(ReportService.SORT_PARAMETER);
    }

    @Test
    void givenUnsupportedFormat_thenThrowsIllegalArgumentException() {
        final ReportType unsupportedType = Arrays.stream(ReportType.values())
                .filter(type -> Arrays.stream(ReportFormat.values())
                        .anyMatch(format -> !type.supportsFormat(format)))
                .findFirst()
                .orElse(null);

        if (unsupportedType == null) {
            return;
        }

        final ReportFormat unsupportedFormat = Arrays.stream(ReportFormat.values())
                .filter(format -> !unsupportedType.supportsFormat(format))
                .findFirst()
                .orElseThrow();

        assertThatThrownBy(() -> reportService.generateReport(
                new HashMap<>(), unsupportedType, unsupportedFormat, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unsupported ReportFormat");

        verifyNoInteractions(jasperReportService);
    }
}
