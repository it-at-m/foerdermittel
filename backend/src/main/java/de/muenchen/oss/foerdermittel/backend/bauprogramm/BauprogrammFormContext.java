package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record BauprogrammFormContext(@NotNull List<BigDecimal> bauprogramme) {
    public BauprogrammFormContext {
        bauprogramme = List.copyOf(bauprogramme);
    }
}
