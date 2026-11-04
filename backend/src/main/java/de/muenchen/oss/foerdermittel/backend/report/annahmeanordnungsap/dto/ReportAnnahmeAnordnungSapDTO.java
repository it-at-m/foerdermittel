package de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ReportAnnahmeAnordnungSapDTO(@NotBlank @Schema(pattern = "\\S") String projnr) {
}
