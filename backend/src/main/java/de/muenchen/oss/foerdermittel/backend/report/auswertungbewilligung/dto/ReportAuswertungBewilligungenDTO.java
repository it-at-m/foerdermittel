package de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.Zuwendungsart;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportAuswertungBewilligungenDTO(
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrVon,
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrBis,
        @Pattern(regexp = "^([1-9]|1[0-2])$") String monat,
        String datum,
        String bez,
        String fb,
        String ua,
        String kurz,

        @Size(max = 30) String pstrasse,

        @Size(max = 30) String pname,
        @Size(max = 7) String projnr,

        String sgt,
        String sbl,
        String bpg,
        Krisofp krisofp,
        Zuwendungsart zuwendungsart,

        String fag,
        String erfolgt,
        String notizen,

        @NotNull ReportAuswertungBewilligungenSort sort,

        @NotNull ReportFormat type) {
}
