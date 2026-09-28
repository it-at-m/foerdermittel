package de.muenchen.oss.foerdermittel.backend.report.dto;

import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;


public record ReportFortsetzungsantragDTO(
        String sbl,
        String bez,
        String fag,
        String ofPro,
        @NotNull ReportFormat type) {
}


