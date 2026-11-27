package de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@SuppressWarnings("Avoi")
public record ReportAuswertungVerwendungsnachweisDTO(

        @Pattern(regexp = "^\\d{4}$") String jahr,

        @Pattern(regexp = "^(0?[1-9]|1[0-2])$") String monat,
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
        ReportAuswertungVerwendungsnachweisSort sort

) {
}
