package de.muenchen.oss.foerdermittel.backend.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

@Getter
@RequiredArgsConstructor
public enum ReportFormat {
    PDF("_R", MediaType.APPLICATION_PDF, ".pdf"),
    PDF_FLAT("_FLAT_R", MediaType.APPLICATION_PDF, ".pdf"),
    EXCEL("_CALC_R", MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), ".xlsx");

    private final String fileSuffix;
    private final MediaType contentType;
    private final String fileExtension;
}
