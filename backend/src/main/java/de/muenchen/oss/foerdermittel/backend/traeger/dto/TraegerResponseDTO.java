package de.muenchen.oss.foerdermittel.backend.traeger.dto;

import jakarta.validation.constraints.NotNull;

public record TraegerResponseDTO(@NotNull String id, @NotNull Integer kurzform, @NotNull String bezeichnung) {
}
