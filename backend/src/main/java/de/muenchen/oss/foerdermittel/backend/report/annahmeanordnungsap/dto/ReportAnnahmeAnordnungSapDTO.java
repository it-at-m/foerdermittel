package de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.dto;

import jakarta.validation.constraints.NotBlank;

public record ReportAnnahmeAnordnungSapDTO(@NotBlank String projnr) {
}
