package de.muenchen.oss.foerdermittel.backend.referat.dto;

import jakarta.validation.constraints.NotNull;

/// @param refnr
/// @param bezeichnung
public record ReferatFormContextDTO(
        @NotNull String refnr,
        @NotNull String bezeichnung) {
}
