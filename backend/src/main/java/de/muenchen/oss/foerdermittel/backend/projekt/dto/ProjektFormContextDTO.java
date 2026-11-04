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
 * @param fobFb
 * @param stadtbezirk
 * @param uasUa
 * @param kurKurzbez
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
        @NotNull BigDecimal fobFb,
        @NotNull BigDecimal bezStadtbezirk,
        String uasUa,
        String kurKurzbez,
        String krnKrhname,
        Krhzweck krhzweck,
        Krisofp krisofp,
        BigDecimal sgtSiedlungsgebiet,
        BigDecimal bpgBauprogramm,
        String projart,
        Boolean refinanzierbar,
        Boolean kauf,
        BigDecimal kripplatz,
        BigDecimal kigaplatz,
        BigDecimal hortplatz,
        String notizen) {
}
