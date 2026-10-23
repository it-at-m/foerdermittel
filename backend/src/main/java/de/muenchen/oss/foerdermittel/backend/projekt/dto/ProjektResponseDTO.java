package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krhzweck;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProjektResponseDTO(
        @NotNull String projnr,
        @NotNull String uasUa,
        @NotNull String jahr,
        @NotNull String lfdnr1,
        @NotNull String lfdnr2,
        @NotNull String fobFb,
        @NotNull String kurzbez,
        String stadtbezirk,
        String krhname,
        Krhzweck krhzweck,
        Krisofp krisofp,
        String siedlungsgebiet,
        String bauprogramm,
        String pname,
        String pstrasse,
        String projart,
        Boolean refinanzierbar,
        Boolean kauf,
        BigDecimal kripplatz,
        BigDecimal kigaplatz,
        BigDecimal hortplatz,
        String notizen) {
}
