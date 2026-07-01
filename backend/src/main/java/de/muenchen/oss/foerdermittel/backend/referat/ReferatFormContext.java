package de.muenchen.oss.foerdermittel.backend.referat;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ReferatFormContext(@NotNull List<BigDecimal> referatIds) {
    public ReferatFormContext {
        referatIds = List.copyOf(referatIds);
    }
}
