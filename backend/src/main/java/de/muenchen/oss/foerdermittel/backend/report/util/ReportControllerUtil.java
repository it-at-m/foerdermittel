package de.muenchen.oss.foerdermittel.backend.report.util;

import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

public final class ReportControllerUtil {

    private ReportControllerUtil() {
        // Utility class
    }

    public static void setMetadata(final HttpServletResponse response, final GeneratedReport generatedReport) {
        response.setContentType(generatedReport.contentType().toString());
        response.setHeader(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + generatedReport.fileName() + "\"");
    }
}
