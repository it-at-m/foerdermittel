package de.muenchen.oss.foerdermittel.backend.hhplan.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/// @param hhjJahr
/// @param fipo
public record ReportHhplanFormContextDTO(
        @NotNull BigDecimal hhjJahr,
        @NotNull String fipo) {
}
