package de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto;

import jakarta.validation.constraints.NotNull;

public record StadtbezirkResponseDTO(@NotNull String id, @NotNull Integer stadtbezirk, @NotNull String bezeichnung) {
}
