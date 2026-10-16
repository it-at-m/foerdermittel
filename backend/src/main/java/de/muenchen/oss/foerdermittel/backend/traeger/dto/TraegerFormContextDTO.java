package de.muenchen.oss.foerdermittel.backend.traeger.dto;

import jakarta.validation.constraints.*;

/// DTO for [de.muenchen.oss.foerdermittel.backend.publikation.Publikation] to be used in
/// other FormContexts other than its own.
///
/// @param kurzform
/// @param bezeichnung
public record TraegerFormContextDTO(
        @NotNull String kurzform,
        @NotNull String bezeichnung) {
}
