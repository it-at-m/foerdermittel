package de.muenchen.oss.foerdermittel.backend.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

@Getter
@RequiredArgsConstructor
public enum ReportFormat {
    PDF(MediaType.APPLICATION_PDF, ".pdf"),
    EXCEL(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), ".xlsx");

    private final MediaType contentType;
    private final String fileExtension;
}
