package de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReportAuswertungTermineDTO(
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahr,
        @Min(1) @Max(12) Integer monat,
        String bez,
        String fb,
        String ua,
        String kurz,
        @Size(max = 30) String pstrasse,
        @Size(max = 30) String pname,
        String sbl,
        boolean zukunft,
        boolean ueb,

        @NotNull ReportAuswertungTermineSort sort) {
}
