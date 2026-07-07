package de.muenchen.oss.foerdermittel.backend.bauleitung.dto;

import jakarta.validation.constraints.NotNull;

public record BauleitungResponseDTO(@NotNull String id, @NotNull String bauleitung, @NotNull String bezeichnung) {
}
