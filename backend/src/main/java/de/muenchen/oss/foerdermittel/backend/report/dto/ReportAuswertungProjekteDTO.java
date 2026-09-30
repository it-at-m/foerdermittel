package de.muenchen.oss.foerdermittel.backend.report.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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
        String kauf,
        String offen,
        String relevant,
        @NotNull ReportAuwertungProjektSort sort) {
}
