package de.muenchen.oss.foerdermittel.backend.hhplan.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record HhplanResponseDTO(
        @NotNull String id,
        @NotNull String proprojnr,
        @NotNull BigDecimal hhjJahr,
        @NotNull String fipo,
        String notizen) {
}
