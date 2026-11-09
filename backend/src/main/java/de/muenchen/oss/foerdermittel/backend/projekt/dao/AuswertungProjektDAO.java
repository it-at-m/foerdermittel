package de.muenchen.oss.foerdermittel.backend.projekt.dao;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.NotNull;

public record AuswertungProjektDAO(
        @NotNull String jahr,
        String pstrasse,
        String pname,
        Krisofp krisofp) {
}
