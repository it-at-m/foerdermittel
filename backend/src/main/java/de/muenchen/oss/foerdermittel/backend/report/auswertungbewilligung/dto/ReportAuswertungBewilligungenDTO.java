package de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.Zuwendungsart;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportAuswertungBewilligungenDTO(
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrVon,
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrBis,
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
