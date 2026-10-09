package de.muenchen.oss.foerdermittel.backend.report.dto;

import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReportHaushaltsplanungDTO(
        @NotBlank @Pattern(regexp = "\\d{4}") String haushaltsjahr,
        String fb,
        String fipo,
        String sbl,
        String bez,
        String hh,
        @NotNull ReportHaushaltsplanungSort sort,
        @NotNull ReportFormat type) {
}
