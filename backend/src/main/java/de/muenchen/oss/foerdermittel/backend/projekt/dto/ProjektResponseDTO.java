package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krhzweck;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProjektResponseDTO(
        @NotNull String id,
        @NotNull String projnr,
        @NotNull String uasUa,
        @NotNull Integer jahr,
        @NotNull Integer lfdnr1,
        @NotNull Integer lfdnr2,
        @NotNull BigDecimal fobFb,
        @NotNull String kurKurzbez,
        BigDecimal bezStadtbezirk,
        String krnKrhname,
        Krhzweck krhzweck,
        Krisofp krisofp,
        BigDecimal sgtSiedlungsgebiet,
        BigDecimal bpgBauprogramm,
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
