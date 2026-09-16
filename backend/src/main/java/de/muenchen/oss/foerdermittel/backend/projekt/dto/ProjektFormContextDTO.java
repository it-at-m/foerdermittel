package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for Projekt to be used in other FormContexts other than its own.
 *
 * @param projnr
 * @param pname
 * @param pstrasse
 * @param foerderbereich
 */
public record ProjektFormContextDTO(
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull String foerderbereich) {
}
