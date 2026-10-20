package de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReportProjektuebersichtMsDTO(

        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahr,
        String sbl,
        String bez,
        String fb,
        String ua,
        String kurz,
        String offen,
        String vn,
        Krisofp krisofp,
        String sgt,
        String bpg,
        @NotNull ReportFormat type,
        @NotNull ReportProjektuebersichtMsSort sort) {

}
