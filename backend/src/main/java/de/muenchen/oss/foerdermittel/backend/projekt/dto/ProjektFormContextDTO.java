package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krhzweck;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO for Projekt to be used in other FormContexts other than its own.
 *
 * @param projnr
 * @param pname
 * @param pstrasse
 * @param foerderbereich
 * @param stadtbezirk
 */
public record ProjektFormContextDTO(
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull String foerderbereich,
        @NotNull BigDecimal stadtbezirk,
        String uasUa,
        String kurzbez,
        String krhname,
        Krhzweck krhzweck,
        Krisofp krisofp,
        BigDecimal siedlungsgebiet,
        BigDecimal bauprogramm,
        String projart,
        Boolean refinanzierbar,
        Boolean kauf,
        BigDecimal kripplatz,
        BigDecimal kigaplatz,
        BigDecimal hortplatz,
        String notizen) {
}
