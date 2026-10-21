package de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public record ReportProjektuebersichtMsDTO(

        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahr,
        String sbl,
        @Pattern(regexp = "^\\d*$") String bez,
        @Pattern(regexp = "^\\d*$") String fb,
        String ua,
        String kurz,
        String offen,
        String vn,
        Krisofp krisofp,
        @Pattern(regexp = "^\\d*$") String sgt,
        @Pattern(regexp = "^\\d*$") String bpg,
        @NotNull ReportFormat type,
        @NotNull ReportProjektuebersichtMsSort sort) {

}
