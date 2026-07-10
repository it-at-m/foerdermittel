package de.muenchen.oss.foerdermittel.backend.publikation.dto;

import jakarta.validation.constraints.NotNull;

public record PublikationResponseDTO(@NotNull String id, @NotNull String kurzform, @NotNull String bezeichnung) {
}
