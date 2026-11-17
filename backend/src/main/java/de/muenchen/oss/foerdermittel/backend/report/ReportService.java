package de.muenchen.oss.foerdermittel.backend.report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportService {

    public static final String SORT_PARAMETER = "P_SORT";
    private final JasperReportService jasperReportService;

    /// Utility function to create a [GeneratedReport].
    ///
    /// @param jasperParameters parameters to fill the report with
    /// @param reportType type of the report to generate
    /// @param reportFormat format of the report to generate
    /// @param sort sort parameter (SQL statement) to use for the Jasper report (passed seperate due to
    ///            SQL injection prevention)
    /// @return the generated report with file metadata
    public GeneratedReport generateReport(
            final Map<String, Object> jasperParameters,
            final ReportType reportType,
            final ReportFormat reportFormat,
            final String sort) {

        checkReportFormat(reportType, reportFormat);

        if (sort != null) {
            jasperParameters.put(SORT_PARAMETER, sort);
        } else {
            jasperParameters.remove(SORT_PARAMETER);
        }

        return new GeneratedReport(
                getDownloadFileName(reportType, reportFormat),
                reportFormat.getContentType(),
                outputStream -> jasperReportService.generateReportWithParameters(
                        reportType,
                        reportFormat,
                        jasperParameters,
                        outputStream));
    }

    /// Checks if a given [ReportFormat] is valid for a given [ReportType].
    ///
    /// @param reportType the ReportType to check against
    /// @param reportFormat the ReportFormat to check
    private static void checkReportFormat(final ReportType reportType, final ReportFormat reportFormat) {
        if (!reportType.supportsFormat(reportFormat)) {
            throw new IllegalArgumentException(
                    "Unsupported ReportFormat: " + reportFormat + " for ReportType: " + reportType);
        }
    }

    /// Calculates the file name depending on the desires [ReportType] and [ReportFormat] and uses
    /// timestamps as prefixes
    ///
    /// @param reportType requested report type
    /// @param reportFormat requested report format
    /// @return file name of the file to be generated
    private static String getDownloadFileName(final ReportType reportType, final ReportFormat reportFormat) {
        final String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return reportType.getFileName() + reportFormat.getFileSuffix() + "_" + timestamp + reportFormat.getFileExtension();
    }

}
