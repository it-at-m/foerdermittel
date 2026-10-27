package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.NotNull;

public record ProjektResponseDTO(
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull String fobFb,
        @NotNull String stadtbezirk,
        @NotNull String kurKurzbez,
        @NotNull String uasUa,
        @NotNull String jahr,
        @NotNull String lfdnr1,
        @NotNull String lfdnr2,
        @NotNull Krisofp krisofp) {
}
