package de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportAuswertungAbrufeDTO(
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrAbrufVon,
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrAbrufBis,
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrErhaltVon,
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrErhaltBis,
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
