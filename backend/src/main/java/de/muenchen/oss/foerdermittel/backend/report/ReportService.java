package de.muenchen.oss.foerdermittel.backend.report;

import de.muenchen.oss.foerdermittel.backend.report.dto.ReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichwortbereicheDTO;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportStichwortbereicheFormContext;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.StichwortbereichService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportService {

    public static final String SORT_PARAMETER = "P_SORT";

    private final StichwortbereichService stichwortbereichService;
    private final JasperReportService jasperReportService;
    private final ReportMapper reportMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportStichwortbereiche(
            final ReportStichwortbereicheDTO parameters) {
        stichwortbereichService.checkExistsById(parameters.bereich());
        return generateReport(reportMapper.toJasperParameters(parameters), ReportType.FMW_ABLAGEINDEX, ReportFormat.PDF,
                "ORDER BY stb_bereich ASC, nr ASC, wort ASC");
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportStichwortbereicheFormContext getReportStichwortbereicheFormContext() {
        log.info("Get ReportStichwortbereiche form context");
        return new ReportStichwortbereicheFormContext(stichwortbereichService.getStichwortbereichFormContextDTOs());
    }

    /**
     * Utility function to create a {@link GeneratedReport}.
     *
     * @param jasperParameters parameters to fill the report with
     * @param reportType type of the report to generate
     * @param reportFormat format of the report to generate
     * @param sort optional sort parameter to pass manually instead of in the mapped parameters
     * @return the generated report with file metadata
     */
    private GeneratedReport generateReport(
            final Map<String, Object> jasperParameters,
            final ReportType reportType,
            final ReportFormat reportFormat,
            final String sort) {

        checkReportFormat(reportType, reportFormat);

        if (sort != null && !jasperParameters.containsKey(SORT_PARAMETER)) {
            jasperParameters.put(SORT_PARAMETER, sort);
        }

        return new GeneratedReport(
                getDownloadFileName(reportType, reportFormat),
                reportFormat.getContentType(),
                outputStream -> {
                    jasperReportService.generateReportWithParameters(
                            reportType,
                            reportFormat,
                            jasperParameters,
                            outputStream);
                });
    }

    /**
     * Checks if a given reportFormat is valid for a given ReportType.
     *
     * @param reportType the ReportType to check against
     * @param reportFormat the ReportFormat to check
     */
    private static void checkReportFormat(final ReportType reportType, final ReportFormat reportFormat) {
        if (!reportType.supportsFormat(reportFormat)) {
            throw new IllegalArgumentException(
                    "Unsupported ReportFormat: " + reportFormat + " for ReportType: " + reportType);
        }
    }

    /**
     * Calculates the file name depending on the desires report type and report format and uses
     * timestamps as prefixes
     *
     * @param reportType requested report type
     * @param reportFormat requested report format
     * @return file name of the file to be generated
     */
    private static String getDownloadFileName(final ReportType reportType, final ReportFormat reportFormat) {
        final String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return reportType.getFileName() + reportFormat.getFileSuffix() + "_" + timestamp + reportFormat.getFileExtension();
    }

}
