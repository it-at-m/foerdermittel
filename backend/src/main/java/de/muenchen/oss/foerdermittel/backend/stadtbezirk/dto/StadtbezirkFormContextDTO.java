package de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto;

import jakarta.validation.constraints.NotNull;

/// @param stadtbezirk
/// @param bezeichnung
public record StadtbezirkFormContextDTO(
        @NotNull Integer stadtbezirk,
        @NotNull String bezeichnung) {
}
