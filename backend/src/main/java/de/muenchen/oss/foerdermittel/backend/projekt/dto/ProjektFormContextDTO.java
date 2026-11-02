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
 * @param uasUa
 * @param kurzbez
 * @param krhname
 * @param krhzweck
 * @param krisofp
 * @param siedlungsgebiet
 * @param bauprogramm
 * @param projart
 * @param refinanzierbar
 * @param kauf
 * @param kripplatz
 * @param kigaplatz
 * @param hortplatz
 * @param notizen
 */
public record ProjektFormContextDTO(
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull BigDecimal foerderbereich,
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
