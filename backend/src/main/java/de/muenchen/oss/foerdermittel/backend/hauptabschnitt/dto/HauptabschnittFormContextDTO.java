package de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto;

import jakarta.validation.constraints.NotNull;

/// DTO for [de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt] to be used in other FormContexts other than its own.
///
/// @param ha
/// @param bezeichnung
public record HauptabschnittFormContextDTO(
        @NotNull String ha,
        @NotNull String bezeichnung) {
}
