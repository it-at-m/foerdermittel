package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.NotNull;

public record ReportAuswertungAntragFormContextDTO(
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse) {
}
