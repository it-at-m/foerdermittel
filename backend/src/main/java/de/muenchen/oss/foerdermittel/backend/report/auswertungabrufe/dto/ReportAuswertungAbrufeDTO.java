package de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReportAuswertungAbrufeDTO(
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrAbrufVon,
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrAbrufBis,
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrErhaltVon,
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrErhaltBis,
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
        String ref,
        String krh,

        String fag,
        String offen,
        String ohnebewill,
        String erh,
        String nichterh,

        @NotNull ReportAuswertungAbrufeSort sort,

        @NotNull ReportFormat type) {
}
