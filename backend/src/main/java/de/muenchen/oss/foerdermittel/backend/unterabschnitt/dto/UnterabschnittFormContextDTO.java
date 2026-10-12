package de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto;

import jakarta.validation.constraints.NotNull;

/// @param ua
/// @param bezeichnung
public record UnterabschnittFormContextDTO(@NotNull String ua,
        @NotNull String bezeichnung) {
}
