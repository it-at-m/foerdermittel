package de.muenchen.oss.foerdermittel.backend.report.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportAuswertungProjekteDTO(
        @NotBlank
        @Pattern(regexp = "\\d{4}")
        String jahr,

        String bez,
        String fb,
        String ua,
        String kurz,

        @Size(max = 30)
        String pstrasse,

        @Size(max = 30)
        String pname,

        String sgt,
        String sbl,
        String bpg,
        Krisofp krisofp,

        String kauf,
        String offen,
        String relevant,

        @NotNull
        ReportAuwertungProjektSort sort) {
}
