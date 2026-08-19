package de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto;

import jakarta.validation.constraints.NotNull;

public record UnterabschnittResponseDTO(@NotNull String id, @NotNull String ua, @NotNull String bezeichnung, @NotNull String hasHa,
        @NotNull String haBezeichnung) {
}
