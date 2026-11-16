package de.muenchen.oss.foerdermittel.backend.projekt.dao;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.NotNull;

public record AuswertungProjektDAO(
        @NotNull String jahr,
        String pname,
        String pstrasse,
        Krisofp krisofp) {
}
