package de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto;

import jakarta.validation.constraints.NotNull;

public record StichwortbereichResponseDTO(@NotNull String id, @NotNull String bereich, @NotNull String bezeichnung) {
}
