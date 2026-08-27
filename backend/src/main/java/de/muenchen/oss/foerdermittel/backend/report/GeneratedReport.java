package de.muenchen.oss.foerdermittel.backend.report;

import org.springframework.http.MediaType;

/**
 * Object that holds all relevant data (file metadata + output stream writer) required for the API layer to prepare the correct response
 * for file download.
 *
 * @param fileName file name of the file
 * @param contentType content type of the file
 * @param writer interface to write back into the output stream of the controller
 */
public record GeneratedReport(
        String fileName,
        MediaType contentType,
        ReportWriter writer) {
}
