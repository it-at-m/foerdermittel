package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.NotNull;

public record ProjektResponseDTO(@NotNull String projnr, @NotNull String pname, @NotNull String pstrasse, @NotNull String foerderbereich,
        @NotNull Krisofp krisofp) {
}
