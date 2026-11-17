package de.muenchen.oss.foerdermittel.backend.foerderbereich.dto;

import jakarta.validation.constraints.NotNull;

/// @param fb
/// @param bezeichnung
public record FoerderbereichFormContextDTO(
        @NotNull Integer fb,
        @NotNull String bezeichnung) {
}
