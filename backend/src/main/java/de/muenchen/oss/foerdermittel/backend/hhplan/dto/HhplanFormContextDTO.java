package de.muenchen.oss.foerdermittel.backend.hhplan.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/// @param hhjJahr
/// @param fipo
public record HhplanFormContextDTO(
        @NotNull BigDecimal hhjJahr,
        @NotNull String proProjnr,
        String fipo,
        BigDecimal mitteleinplanung,
        BigDecimal bewilligungVoj,
        BigDecimal bewilligungHhj,
        BigDecimal erhaltenVoJ,
        BigDecimal erhaltenHhj,
        BigDecimal baustandaktuell,
        BigDecimal an,
        BigDecimal n1,
        BigDecimal n2,
        String notizen

) {
}
