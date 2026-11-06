package de.muenchen.oss.foerdermittel.backend.hhjahr.dto;

import jakarta.validation.constraints.NotNull;

/// @param hhjahr
/// @param hhbezeichnung
/// @param notizen
public record HhjahrFormContextDTO(
        @NotNull String hhjahr,
        @NotNull String hhbezeichnung,
        String notizen) {
}
