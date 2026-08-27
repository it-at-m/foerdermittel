package de.muenchen.oss.foerdermittel.backend.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportStichwortbereicheDTO(@NotBlank @Size(min = 1, max = 30) @Pattern(regexp = "^[A-Z0-9\\-]+$") String bereich) {
}
