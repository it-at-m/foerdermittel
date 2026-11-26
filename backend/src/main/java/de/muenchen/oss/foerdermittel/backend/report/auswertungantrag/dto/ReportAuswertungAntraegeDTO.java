package de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Antragstyp;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReportAuswertungAntraegeDTO(
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrVon,
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrBis,
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
        Antragstyp antragstyp,

        String fag,
        String offen,
        String relevant,

        @NotNull ReportAuswertungAntraegeSort sort,

        @NotNull ReportFormat type) {
}
