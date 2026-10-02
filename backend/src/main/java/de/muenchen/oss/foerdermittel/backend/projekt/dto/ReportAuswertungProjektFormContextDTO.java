package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ReportAuswertungProjektFormContextDTO(
        @NotNull String jahr,
        String pstrasse,
        String pname,
        Krisofp krisofp) {
}
