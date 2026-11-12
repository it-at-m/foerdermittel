package de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static de.muenchen.oss.foerdermittel.backend.report.validation.ValidationPatterns.SUPPORTED_YEAR_RANGE_PATTERN;

public record ReportAuswertungAbrufeDTO(
        @Pattern(regexp = SUPPORTED_YEAR_RANGE_PATTERN) String jahrAbrufVon,
        @Pattern(regexp = SUPPORTED_YEAR_RANGE_PATTERN) String jahrAbrufBis,
        @Pattern(regexp = SUPPORTED_YEAR_RANGE_PATTERN) String jahrErhaltVon,
        @Pattern(regexp = SUPPORTED_YEAR_RANGE_PATTERN) String jahrErhaltBis,
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
