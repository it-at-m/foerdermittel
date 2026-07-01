package de.muenchen.oss.foerdermittel.backend.referat.dto;

import jakarta.validation.constraints.NotNull;

public record ReferatResponseDTO(@NotNull String id,
        @NotNull Integer refnr,
        @NotNull String bezeichnung) {
}
