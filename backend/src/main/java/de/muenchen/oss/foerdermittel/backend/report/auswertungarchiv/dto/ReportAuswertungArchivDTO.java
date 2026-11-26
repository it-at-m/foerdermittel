package de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto.ReportAuswertungAntraegeSort;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReportAuswertungArchivDTO(

        String bez,
        String fb,
        String ua,
        String kurz,
        @Size(max = 30) String pstrasse,
        @Size(max = 30) String pname,
        @Size(max = 7) String projnr,
        Krisofp krisofp,
        boolean film,
        boolean filmohne,
        boolean offen,
        String planab,
        String spseit,
        boolean op,
        boolean mp,

        @NotNull ReportAuswertungAntraegeSort sort,

        @NotNull ReportFormat type) {
}
