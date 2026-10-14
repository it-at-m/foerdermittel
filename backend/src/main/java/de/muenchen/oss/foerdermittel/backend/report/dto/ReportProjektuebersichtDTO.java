package de.muenchen.oss.foerdermittel.backend.report.dto;

import jakarta.validation.constraints.NotBlank;

public record ReportProjektuebersichtDTO(@NotBlank String projnr, boolean notiz) {
}
