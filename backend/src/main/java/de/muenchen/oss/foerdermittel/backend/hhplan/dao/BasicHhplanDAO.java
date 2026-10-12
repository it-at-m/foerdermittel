package de.muenchen.oss.foerdermittel.backend.hhplan.dao;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BasicHhplanDAO(@NotNull BigDecimal hhjJahr, String fipo) {
}
