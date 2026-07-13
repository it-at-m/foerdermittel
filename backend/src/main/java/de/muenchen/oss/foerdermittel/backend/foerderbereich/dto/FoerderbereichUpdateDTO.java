package de.muenchen.oss.foerdermittel.backend.foerderbereich.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FoerderbereichUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung,
        @NotNull Boolean finanzausgleich,
        @NotNull Boolean jahresstatistik,
        @NotNull Boolean kindergarten,
        @NotNull Boolean nichtRelevant) {
}
