package de.muenchen.oss.foerdermittel.backend.report.util;

import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

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

    /**
     * Converts the given value into a LIKE parameter by trimming leading and trailing whitespace
     * and wrapping the value with '%' wildcards.
     * Returns {@code null} if the value is blank or equals "null" (case-insensitive).
     *
     * @param value the value to convert
     * @return the formatted LIKE parameter, or {@code null} if the value is blank or equals "null"
     */

    public static String toLikeParameter(final String value) {
        if (!StringUtils.hasText(value)
                || "null".equalsIgnoreCase(value)) {
            return null;
        }

        return "%" + value.trim() + "%";
    }

    public static String toTrimmedStringOrNull(final Object value) {
        if (value == null) {
            return null;
        }

        final String strValue = value.toString();

        if (!StringUtils.hasText(strValue)) {
            return null;
        }

        return strValue.trim();
    }

    public static String toDateParameter(final LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
