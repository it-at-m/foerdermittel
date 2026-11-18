package de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ReportAnnahmeAnordnungSapDTO(@NotBlank @Pattern(regexp = "\\S") String projnr) {
}
