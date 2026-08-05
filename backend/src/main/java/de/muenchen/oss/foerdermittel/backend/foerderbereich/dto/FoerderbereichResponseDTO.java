package de.muenchen.oss.foerdermittel.backend.foerderbereich.dto;

import jakarta.validation.constraints.NotNull;

public record FoerderbereichResponseDTO(@NotNull String id,
        @NotNull Integer fb,
        @NotNull String bezeichnung,
        @NotNull Boolean finanzausgleich,
        @NotNull Boolean jahresstatistik,
        @NotNull Boolean kindergarten,
        @NotNull Boolean nichtRelevant) {
}
