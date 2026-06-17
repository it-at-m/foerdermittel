package de.muenchen.oss.foerdermittel.backend.bauprogramm.dto;

import jakarta.validation.constraints.NotNull;

public record BauprogrammResponseDTO(@NotNull String id, @NotNull Integer bauprogramm, @NotNull String bezeichnung) {
}
