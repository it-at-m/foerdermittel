package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ReportAuswertungProjektFormContextDTO(
        @NotNull String projnr,
        @NotNull BigDecimal jahr,
        @NotNull String sbl,
        @NotNull String bez,
        @NotNull String fb,
        @NotNull String ua,
        @NotNull String kurz,
        @NotNull String pstrasse,
        @NotNull String pname,
        @NotNull String foerderprogramm,
        @NotNull String sbg,
        @NotNull String bpg) {
}
