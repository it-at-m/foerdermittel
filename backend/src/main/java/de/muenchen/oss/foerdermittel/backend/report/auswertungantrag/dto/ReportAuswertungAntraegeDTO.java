package de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Antrag;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportAuswertungAntraegeDTO(
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrvon,
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrbis,
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
        Antrag atyp,

        String fag,
        String offen,
        String relevant,

        @NotNull ReportAuswertungAntraegeSort sort,

        @NotNull ReportFormat type) {
}
