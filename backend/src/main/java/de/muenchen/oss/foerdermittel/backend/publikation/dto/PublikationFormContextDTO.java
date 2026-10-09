package de.muenchen.oss.foerdermittel.backend.publikation.dto;

import jakarta.validation.constraints.NotNull;

/// DTO for [de.muenchen.oss.foerdermittel.backend.publikation.Publikation] to be used in
/// other FormContexts other than its own.
///
/// @param kurzform
/// @param bezeichnung
public record PublikationFormContextDTO(
        @NotNull String kurzform,
        @NotNull String bezeichnung) {
}
