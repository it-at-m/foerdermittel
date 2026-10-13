package de.muenchen.oss.foerdermittel.backend.report.dto;

import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReportFortsetzungsantragDTO(
        @Pattern(regexp = "^[A-Z0-9]+$") String sbl,
        @Pattern(regexp = "([0-9]|[1-9][0-9])") String bez,
        String fag,
        String ofPro,
        @NotNull ReportFormat type) {
}
