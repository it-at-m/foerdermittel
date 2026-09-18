package de.muenchen.oss.foerdermittel.backend.report.dto;

import jakarta.validation.constraints.NotBlank;

public record ReportAuswertungProjekteDTO(
        String projnr,
        @NotBlank String jahr,
        Boolean kauf,
        Boolean offen,
        Boolean relevant ) {
}
