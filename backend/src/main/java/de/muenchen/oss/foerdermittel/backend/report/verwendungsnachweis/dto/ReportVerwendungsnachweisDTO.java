package de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReportVerwendungsnachweisDTO(

        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahr,
        String monat,
        String sbl,
        @Pattern(regexp = "^\\d*$") String bez,
        @Pattern(regexp = "^\\d*$") String fb,
        String ua,
        String krh,
        String pname,
        String pstrasse,
        String kurz,
        String ohneschluss,
        String notizen,
        String fag,
        String win,
        Krisofp krisofp,
        @Pattern(regexp = "^\\d*$") String sgt,
        @Pattern(regexp = "^\\d*$") String bpg,
        @NotNull ReportFormat type,
        ReportVerwendungsnachweisSort sort

) {
}
