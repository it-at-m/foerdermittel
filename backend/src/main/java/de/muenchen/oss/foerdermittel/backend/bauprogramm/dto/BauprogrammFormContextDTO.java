package de.muenchen.oss.foerdermittel.backend.bauprogramm.dto;

import jakarta.validation.constraints.NotNull;

/// @param bauprogramm
/// @param bezeichnung

public record BauprogrammFormContextDTO(
        @NotNull Integer bauprogramm,
        @NotNull String bezeichnung) {
}
