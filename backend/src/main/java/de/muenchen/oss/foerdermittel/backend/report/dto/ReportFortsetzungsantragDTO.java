package de.muenchen.oss.foerdermittel.backend.report.dto;

import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReportFortsetzungsantragDTO(
        String sbl,
        @Pattern(regexp = "\\d*") String bez,
        String fag,
        String ofPro,
        @NotNull ReportFormat type) {
}
