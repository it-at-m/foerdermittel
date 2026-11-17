package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO for Projekt to be used in other FormContexts other than its own.
 *
 * @param projnr
 * @param pname
 * @param pstrasse
 * @param fobFb
 * @param bezStadtbezirk
 */
public record ProjektFormContextDTO(
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull BigDecimal fobFb,
        @NotNull BigDecimal bezStadtbezirk) {
}
