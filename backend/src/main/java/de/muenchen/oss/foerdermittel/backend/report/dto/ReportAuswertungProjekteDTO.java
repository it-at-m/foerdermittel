package de.muenchen.oss.foerdermittel.backend.report.dto;

import jakarta.validation.constraints.NotBlank;

public record ReportAuswertungProjekteDTO(
        @NotBlank String jahr,
        String bez,
        String fb,
        String ua,
        String kurz,
        String pstrasse,
        String pname,
        String sgt,
        String sbl,
        String bpg,
        String krisofp,
        Boolean kauf,
        Boolean offen,
        Boolean relevant) {
}
